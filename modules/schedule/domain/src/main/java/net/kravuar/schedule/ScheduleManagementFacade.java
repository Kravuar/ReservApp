package net.kravuar.schedule;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.schedule.domain.*;
import net.kravuar.schedule.domain.commands.*;
import net.kravuar.schedule.ports.in.ScheduleManagementUseCase;
import net.kravuar.schedule.ports.out.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@AppComponent
@RequiredArgsConstructor
public class ScheduleManagementFacade implements ScheduleManagementUseCase {
    private final ScheduleRetrievalPort scheduleRetrievalPort;
    private final StaffRetrievalPort staffRetrievalPort;
    private final ServiceRetrievalPort serviceRetrievalPort;
    private final ScheduleLockPort scheduleLockPort;
    private final SchedulePersistencePort schedulePersistencePort;
    private final ReservationRetrievalPort reservationRetrievalPort;

    @Override
    public Schedule changeSchedulePatterns(ChangeSchedulePatternsCommand command) {
        try {
            scheduleLockPort.lock(command.scheduleId(), true);

            Schedule schedule = scheduleRetrievalPort.findById(
                    command.scheduleId(),
                    true
            );

            if (scheduleSizeInsufficient(command.patterns(), schedule.getStart(), schedule.getEnd()))
                throw new IllegalStateException("Schedule duration isn't sufficient for provided patterns");

            try {
                scheduleLockPort.lockByStaff(schedule.getStaff().getId(), true);

                throwIfReservationsExist(
                        schedule.getStaff().getId(),
                        schedule.getStart(),
                        schedule.getEnd()
                );

                schedule.setPatterns(command.patterns());
                return schedulePersistencePort.save(schedule);
            } finally {
                scheduleLockPort.lockByStaff(schedule.getStaff().getId(), false);
            }
        } finally {
            scheduleLockPort.lock(command.scheduleId(), false);
        }
    }

    @Override
    public Schedule changeScheduleDuration(ChangeScheduleDurationCommand command) {
        try {
            scheduleLockPort.lock(command.getScheduleId(), true);

            Schedule schedule = scheduleRetrievalPort.findById(
                    command.getScheduleId(),
                    true
            );

            boolean extended = schedule.getStart().isAfter(command.getStart())
                    || schedule.getEnd().isBefore(command.getEnd());

            if (scheduleSizeInsufficient(schedule.getPatterns(), command.getStart(), command.getEnd()))
                throw new IllegalStateException("Schedule duration isn't sufficient for provided patterns");

            try {
                scheduleLockPort.lockByStaff(schedule.getStaff().getId(), true);

                throwIfReservationsExist(
                        schedule.getStaff().getId(),
                        schedule.getStart(),
                        schedule.getEnd()
                );

                if (extended) {
                    List<Schedule> overlapped = scheduleRetrievalPort.findActiveSchedulesByStaffAndService(
                            schedule.getStaff().getId(),
                            schedule.getService().getId(),
                            command.getStart(),
                            command.getEnd()
                    );
                    int amount = overlapped.size();
                    boolean noOverlapOrSelf = amount == 0 || (amount == 1 && overlapped.getFirst().getId().equals(schedule.getId()));
                    if (!noOverlapOrSelf)
                        throw new IllegalArgumentException("Schedule overlapped with other schedules");
                }

                schedule.setStart(command.getStart());
                schedule.setEnd(command.getEnd());

                return schedulePersistencePort.save(schedule);
            } finally {
                scheduleLockPort.lockByStaff(schedule.getStaff().getId(), false);
            }
        } finally {
            scheduleLockPort.lock(command.getScheduleId(), false);
        }
    }

    @Override
    public Schedule createSchedule(CreateScheduleCommand command) {
        try {
            scheduleLockPort.lockByStaff(command.getStaffId(), true);

            if (scheduleSizeInsufficient(command.getPatterns(), command.getStart(), command.getEnd()))
                throw new IllegalStateException("Schedule duration isn't sufficient for provided patterns");

            Staff staff = staffRetrievalPort.findActiveById(command.getStaffId());
            Service service = serviceRetrievalPort.findActiveById(command.getServiceId());

            Schedule newSchedule = new Schedule(
                    null,
                    command.getEnd(),
                    command.getStart(),
                    staff,
                    service,
                    command.getPatterns(),
                    true
            );

            List<Schedule> overlapped = scheduleRetrievalPort.findActiveSchedulesByStaffAndService(
                    newSchedule.getStaff().getId(),
                    newSchedule.getService().getId(),
                    newSchedule.getStart(),
                    newSchedule.getEnd()
            );
            if (!overlapped.isEmpty())
                throw new IllegalStateException("Schedule overlapped with other schedules");

            return schedulePersistencePort.save(newSchedule);
        } finally {
            scheduleLockPort.lockByStaff(command.getStaffId(), false);
        }
    }

    @Override
    public ScheduleExceptionDay addOrUpdateScheduleExceptionDay(CreateScheduleExceptionDayCommand command) {
        Service service = serviceRetrievalPort.findActiveById(command.serviceId());
        Staff staff = staffRetrievalPort.findActiveById(command.staffId());

        try {
            scheduleLockPort.lockByStaff(command.staffId(), true);

            ScheduleExceptionDay exceptionDay = scheduleRetrievalPort.findActiveExceptionDayByStaffAndService(
                    command.staffId(),
                    command.serviceId(),
                    command.date()
            ).orElse(new ScheduleExceptionDay(
                    null,
                    command.date(),
                    staff,
                    service,
                    null
            ));

            throwIfReservationsExist(
                    exceptionDay.getStaff().getId(),
                    exceptionDay.getDate(),
                    exceptionDay.getDate()
            );

            exceptionDay.setReservationSlots(command.reservationSlots());

            return schedulePersistencePort.save(exceptionDay);
        } finally {
            scheduleLockPort.lockByStaff(command.staffId(), false);
        }
    }

    @Override
    public void removeSchedule(RemoveScheduleCommand command) {
        try {
            scheduleLockPort.lock(command.scheduleId(), true);

            Schedule schedule = scheduleRetrievalPort.findById(
                    command.scheduleId(),
                    true
            );

            try {
                scheduleLockPort.lockByStaff(schedule.getStaff().getId(), true);

                throwIfReservationsExist(
                        schedule.getStaff().getId(),
                        schedule.getStart(),
                        schedule.getEnd()
                );

                schedule.setActive(false);
                schedulePersistencePort.save(schedule);
            } finally {
                scheduleLockPort.lockByStaff(schedule.getStaff().getId(), false);
            }
        } finally {
            scheduleLockPort.lock(command.scheduleId(), false);
        }
    }

    private boolean scheduleSizeInsufficient(List<SchedulePattern> patterns, LocalDate start, LocalDate end) {
        Duration cycleDuration = patterns.stream()
                .reduce(
                        Duration.ZERO,
                        (acc, pattern) -> acc.plusDays(pattern.getRepeatDays() + pattern.getPauseDays()),
                        Duration::plus
                );
        return start.until(end)
                .plusDays(1)
                .minusDays(cycleDuration.toDays())
                .isNegative();
    }

    private void throwIfReservationsExist(long staffId, LocalDate start, LocalDate end) {
        // Throw if affected reservations
        if (!reservationRetrievalPort.findAllActiveByStaff(
                staffId,
                start,
                end,
                true
        ).isEmpty()) {
            throw new IllegalStateException("Schedule change would affect existing reservations");
        }
    }
}
