package net.kravuar.schedule;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.schedule.domain.Schedule;
import net.kravuar.schedule.domain.commands.ChangeScheduleDurationCommand;
import net.kravuar.schedule.domain.commands.ChangeSchedulePatternsCommand;
import net.kravuar.schedule.domain.commands.CreateScheduleCommand;
import net.kravuar.schedule.domain.commands.RemoveScheduleCommand;
import net.kravuar.schedule.ports.in.ScheduleManagementUseCase;
import net.kravuar.schedule.ports.out.ScheduleLockPort;
import net.kravuar.schedule.ports.out.SchedulePersistencePort;
import net.kravuar.schedule.ports.out.ScheduleRetrievalPort;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;

@AppComponent
@RequiredArgsConstructor
public class ScheduleManagementFacade implements ScheduleManagementUseCase {
    private final ScheduleRetrievalPort scheduleRetrievalPort;
    private final ScheduleLockPort scheduleLockPort;
    private final SchedulePersistencePort schedulePersistencePort;

    private boolean scheduleSizeInsufficient(Schedule schedule) {
        Duration cycleDuration = schedule.getPatterns().stream()
                .reduce(
                        Duration.ZERO,
                        (acc, pattern) -> acc.plusDays(pattern.getRepeatDays() + pattern.getPauseDays()),
                        Duration::plus
                );
        return Duration.between(schedule.getStart(), schedule.getEnd()).minus(cycleDuration).isNegative();
    }

    @Override
    public void updateSchedulePatterns(ChangeSchedulePatternsCommand command) {
        try {
            scheduleLockPort.lock(command.scheduleId(), true);

            Schedule schedule = scheduleRetrievalPort.findById(command.scheduleId());
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
            try {
                scheduleLockPort.lockByStaff(schedule.getStaff().getId(), true);

                boolean mayOverlap = schedule.getStart().isAfter(command.getStart())
                        || schedule.getEnd().isBefore(command.getEnd());

                schedule.setStart(command.getStart());
                schedule.setEnd(command.getEnd());

                if (scheduleSizeInsufficient(schedule))
                    throw new IllegalStateException("Schedule duration isn't sufficient for provided patterns");

                if (mayOverlap) {
                    List<Schedule> schedules = scheduleRetrievalPort.findByStaffId(schedule.getStaff().getId());
                    boolean overlapped = schedules.stream()
                            .sorted(Comparator.comparing(Schedule::getStart))
                            .dropWhile(preceding -> preceding.getEnd().isBefore(schedule.getStart()))
                            .takeWhile(later -> later.getStart().isBefore(schedule.getEnd()))
                            .findAny()
                            .isPresent();
                    if (overlapped)
                        throw new IllegalArgumentException("Schedule overlapped with other schedules");
                }

                schedulePersistencePort.save(schedule);
            } finally {
                scheduleLockPort.lockByStaff(schedule.getStaff().getId(), false);
            }
        } finally {
            scheduleLockPort.lock(command.getScheduleId(), false);
        }
    }

    @Override
    public void addSchedule(CreateScheduleCommand command) {

    }

    @Override
    public void removeSchedule(RemoveScheduleCommand command) {

    }
}
