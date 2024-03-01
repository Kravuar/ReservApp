package net.kravuar.schedule;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.schedule.domain.Schedule;
import net.kravuar.schedule.domain.halfbreeddomain.ScheduleExceptionDay;
import net.kravuar.schedule.domain.Staff;
import net.kravuar.schedule.domain.commands.RetrieveScheduleByServiceCommand;
import net.kravuar.schedule.domain.commands.RetrieveScheduleByStaffAndServiceCommand;
import net.kravuar.schedule.domain.halfbreeddomain.SchedulePattern;
import net.kravuar.schedule.domain.halfbreeddomain.WorkingHours;
import net.kravuar.schedule.ports.in.ScheduleRetrievalUseCase;
import net.kravuar.schedule.ports.out.ScheduleRetrievalPort;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@AppComponent
@RequiredArgsConstructor
public class ScheduleRetrievalFacade implements ScheduleRetrievalUseCase {
    private final ScheduleRetrievalPort scheduleRetrievalPort;

    @Override
    public Map<LocalDate, List<WorkingHours>> findActiveScheduleByStaffAndService(RetrieveScheduleByStaffAndServiceCommand command) {
        return toPerDay(scheduleRetrievalPort
                        .findActiveByStaffIdAndServiceId(
                                command.getStaffId(),
                                command.getServiceId(),
                                command.getStart(),
                                command.getEnd()
                        ),
                command.getStart(),
                command.getEnd()
        );
    }

    @Override
    public Map<Staff, Map<LocalDate, List<WorkingHours>>> findActiveScheduleByService(RetrieveScheduleByServiceCommand command) {
        Map<Staff, List<Schedule>> schedules = scheduleRetrievalPort.findActiveByServiceId(command.getServiceId(), command.getStart(), command.getEnd());
        return schedules.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> toPerDay(entry.getValue(), command.getStart(), command.getEnd())
        ));
    }

    private Map<LocalDate, List<WorkingHours>> toPerDay(List<Schedule> schedules, LocalDate from, LocalDate to) {
        NavigableMap<LocalDate, Schedule> schedulesMap = new TreeMap<>(schedules.stream()
                .collect(Collectors.toMap(
                        Schedule::getStart,
                        Function.identity())
                ));
        if (schedules.isEmpty())
            return Collections.emptyMap();

        return from
                .datesUntil(to.plusDays(1))
                .collect(toPerDay(schedulesMap));
    }

    private Collector<LocalDate, ?, Map<LocalDate, List<WorkingHours>>> toPerDay(NavigableMap<LocalDate, Schedule> schedules) {
        return Collectors.toMap(
                Function.identity(),
                currentDay -> {
                    // Get the schedule for particular day
                    Schedule currentSchedule = schedules
                            .floorEntry(currentDay)
                            .getValue();

                    // Check if its exceptional day (take latest exception update)
                    Optional<ScheduleExceptionDay> latestExceptionDay = currentSchedule
                            .getExceptionDays().stream()
                            .filter(exceptionDay -> exceptionDay.getDate().equals(currentDay))
                            .findAny();
                    if (latestExceptionDay.isPresent())
                        return latestExceptionDay.get().getWorkingHours();
                    else if (currentSchedule.getPatterns().isEmpty()) // Note: validation layer won't allow this though
                        return Collections.emptyList();
                    else {
                        // Not exceptional day, find from schedule

                        // 36 - daysPassedFromSchedule
                        // (3,2) (5,2) (2,1) - patterns (repeat, pause)
                        // 36 % 15 = 6 - withoutRepeats
                        // 6 - falls into second pattern
                        // 6 - (3+2) = 1 is greater than 0, then we move to (5, 2),
                        // 1 - (5 + 2) = -6, less or equal to 0, stop here

                        int daysPassedFromSchedule = (int) ChronoUnit.DAYS.between(
                                currentSchedule.getStart(),
                                currentDay
                        );
                        int patternsCycleDuration = currentSchedule
                                .getPatterns().stream()
                                .reduce(0,
                                        (acc, pattern) -> acc + pattern.getRepeatDays() + pattern.getPauseDays(),
                                        Integer::sum
                                );

                        int cycleDaysToPass = daysPassedFromSchedule % patternsCycleDuration;
                        int patternIdx = 0;
                        while (cycleDaysToPass > 0) {
                            SchedulePattern pattern = currentSchedule.getPatterns().get(patternIdx);
                            cycleDaysToPass -= pattern.getRepeatDays() + pattern.getPauseDays();
                            ++patternIdx;
                        }
                        return currentSchedule.getPatterns().get(patternIdx).getWorkingHours();
                    }
                }
        );
    }
}
