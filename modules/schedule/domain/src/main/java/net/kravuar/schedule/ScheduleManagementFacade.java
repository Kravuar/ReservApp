package net.kravuar.schedule;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.schedule.domain.Schedule;
import net.kravuar.schedule.domain.Service;
import net.kravuar.schedule.domain.Staff;
import net.kravuar.schedule.domain.commands.ChangeScheduleDurationCommand;
import net.kravuar.schedule.domain.commands.ChangeSchedulePatternsCommand;
import net.kravuar.schedule.domain.commands.CreateScheduleCommand;
import net.kravuar.schedule.domain.commands.RemoveScheduleCommand;
import net.kravuar.schedule.ports.in.ScheduleManagementUseCase;
import net.kravuar.schedule.ports.out.*;

import java.time.Duration;
import java.util.Comparator;
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
    public void updateSchedulePatterns(ChangeSchedulePatternsCommand command) {
        try {
            scheduleLockPort.lock(command.scheduleId(), true);

            Schedule schedule = scheduleRetrievalPort.findActiveById(command.scheduleId());
            schedule.setPatterns(command.patterns());
            if (scheduleSizeInsufficient(schedule))
                throw new IllegalStateException("Schedule duration isn't sufficient for provided patterns");

            // TODO: throw if affected reservations
            schedulePersistencePort.save(schedule);
        } finally {
            scheduleLockPort.lock(command.scheduleId(), false);
        }
    }

    @Override
    public void updateScheduleDuration(ChangeScheduleDurationCommand command) {
        try {
            scheduleLockPort.lock(command.getScheduleId(), true);

            Schedule schedule = scheduleRetrievalPort.findActiveById(command.getScheduleId());

            boolean mayOverlap = schedule.getStart().isAfter(command.getStart())
                    || schedule.getEnd().isBefore(command.getEnd());

            schedule.setStart(command.getStart());
            schedule.setEnd(command.getEnd());

            if (scheduleSizeInsufficient(schedule))
                throw new IllegalStateException("Schedule duration isn't sufficient for provided patterns");

            try {
                scheduleLockPort.lockByStaff(schedule.getStaff().getId(), true);

                if (mayOverlap) {
                    List<Schedule> overlapped = getStaffSchedulesOverlap(schedule);
                    int amount = overlapped.size();
                    boolean noOverlapOrSelf = amount == 0 || (amount == 1 && overlapped.getFirst().getId().equals(schedule.getId()));
                    if (!noOverlapOrSelf)
                        throw new IllegalArgumentException("Schedule overlapped with other schedules");
                }

                schedulePersistencePort.save(schedule);
            } finally {
                scheduleLockPort.lockByStaff(schedule.getStaff().getId(), false);
            }
            // TODO: throw if affected reservations
        } finally {
            scheduleLockPort.lock(command.getScheduleId(), false);
        }
    }

    @Override
    public void addSchedule(CreateScheduleCommand command) {
        try {
            scheduleLockPort.lockByStaff(command.getStaffId(), true);

            Staff staff = staffRetrievalPort.findActiveById(command.getStaffId());
            Service service = serviceRetrievalPort.findActiveById(command.getServiceId());

            Schedule newSchedule = new Schedule(
                    null,
                    command.getEnd(),
                    command.getStart(),
                    staff,
                    service,
                    command.getPatterns(),
                    command.getExceptionDays(),
                    true
            );

            if (scheduleSizeInsufficient(newSchedule))
                throw new IllegalStateException("Schedule duration isn't sufficient for provided patterns");

            if (!getStaffSchedulesOverlap(newSchedule).isEmpty())
                throw new IllegalStateException("Schedule overlapped with other schedules");

            schedulePersistencePort.save(newSchedule);
        } finally {
            scheduleLockPort.lockByStaff(command.getStaffId(), false);
        }
    }

    @Override
    public void removeSchedule(RemoveScheduleCommand command) {
        Schedule schedule = scheduleRetrievalPort.findActiveById(command.scheduleId());
        schedule.setActive(false);
        schedulePersistencePort.save(schedule);
    }

    private boolean scheduleSizeInsufficient(Schedule schedule) {
        Duration cycleDuration = schedule.getPatterns().stream()
                .reduce(
                        Duration.ZERO,
                        (acc, pattern) -> acc.plusDays(pattern.getRepeatDays() + pattern.getPauseDays()),
                        Duration::plus
                );
        return Duration.between(schedule.getStart(), schedule.getEnd()).minus(cycleDuration).isNegative();
    }

    private List<Schedule> getStaffSchedulesOverlap(Schedule schedule) {
        List<Schedule> schedules = scheduleRetrievalPort.findActiveByStaffId(schedule.getStaff().getId());
        return schedules.stream()
                .sorted(Comparator.comparing(Schedule::getStart))
                .dropWhile(preceding -> preceding.getEnd().isBefore(schedule.getStart()))
                .takeWhile(later -> later.getStart().isBefore(schedule.getEnd()))
                .toList();
    }
}
