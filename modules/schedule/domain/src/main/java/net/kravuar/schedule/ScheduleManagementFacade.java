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

            schedule.setPatterns(command.patterns());

            // TODO: throw if affected reservations
            return schedulePersistencePort.save(schedule);
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

            // TODO: throw if would affect reservations
            boolean mayOverlap = schedule.getStart().isAfter(command.getStart())
                    || schedule.getEnd().isBefore(command.getEnd());


            if (scheduleSizeInsufficient(schedule.getPatterns(), command.getStart(), command.getEnd()))
                throw new IllegalStateException("Schedule duration isn't sufficient for provided patterns");

            schedule.setStart(command.getStart());
            schedule.setEnd(command.getEnd());

            try {
                if (mayOverlap) {
                    scheduleLockPort.lockByStaffAndService(
                            schedule.getStaff().getId(),
                            schedule.getService().getId(),
                            true
                    );

                    List<Schedule> overlapped = scheduleRetrievalPort.findActiveSchedulesByStaffAndService(
                            schedule.getStaff().getId(),
                            schedule.getService().getId(),
                            schedule.getStart(),
                            schedule.getEnd()
                    );
                    int amount = overlapped.size();
                    boolean noOverlapOrSelf = amount == 0 || (amount == 1 && overlapped.getFirst().getId().equals(schedule.getId()));
                    if (!noOverlapOrSelf)
                        throw new IllegalArgumentException("Schedule overlapped with other schedules");
                }

                return schedulePersistencePort.save(schedule);
            } finally {
                scheduleLockPort.lockByStaffAndService(
                        schedule.getStaff().getId(),
                        schedule.getService().getId(),
                        false
                );
            }
        } finally {
            scheduleLockPort.lock(command.getScheduleId(), false);
        }
    }

    @Override
    public Schedule createSchedule(CreateScheduleCommand command) {
        try {
            scheduleLockPort.lockByStaffAndService(command.getStaffId(), command.getServiceId(), true);

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
            scheduleLockPort.lockByStaffAndService(command.getStaffId(), command.getServiceId(), false);
        }
    }

    @Override
    public ScheduleExceptionDay addScheduleExceptionDay(CreateScheduleExceptionDayCommand command) {
        Service service = serviceRetrievalPort.findActiveById(command.serviceId());
        Staff staff = staffRetrievalPort.findActiveById(command.staffId());

        ScheduleExceptionDay exceptionDay = scheduleRetrievalPort.findActiveExceptionDayByStaffAndService(
                command.staffId(),
                command.serviceId(),
                command.date()
        ).orElse(new ScheduleExceptionDay(
                null,
                command.date(),
                staff,
                service,
                command.workingHours()
        ));

        return schedulePersistencePort.save(exceptionDay);
    }

    @Override
    public void removeSchedule(RemoveScheduleCommand command) {
        Schedule schedule = scheduleRetrievalPort.findById(
                command.scheduleId(),
                true
        );
        schedule.setActive(false);
        schedulePersistencePort.save(schedule);
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
}
