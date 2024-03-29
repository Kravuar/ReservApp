package net.kravuar.schedule.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.kravuar.schedule.domain.util.period.Period;
import net.kravuar.schedule.domain.weak.ReservationSlot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Schedule implements Period<LocalDate> {
    private Long id;
    private LocalDate start;
    private LocalDate end;
    private Staff staff;
    private Service service;
    private List<SchedulePattern> patterns;

    private final LocalDateTime createdAt = LocalDateTime.now();
    private boolean active;

    public static NavigableMap<LocalDate, SortedSet<ReservationSlot>> asPerDay(List<Schedule> schedules, Map<LocalDate, ScheduleExceptionDay> exceptionDays, LocalDate from, LocalDate to) {
        NavigableMap<LocalDate, Schedule> schedulesMap = new TreeMap<>(schedules.stream()
                .collect(Collectors.toMap(
                        Schedule::getStart,
                        Function.identity())
                ));

        return from
                .datesUntil(to.plusDays(1))
                .collect(Collectors.toMap(
                                Function.identity(),
                                currentDay -> {
                                    ScheduleExceptionDay exceptionDay = exceptionDays.get(currentDay);
                                    if (exceptionDay != null)
                                        return exceptionDay.getReservationSlots();

                                    // Get the schedule for particular day
                                    Map.Entry<LocalDate, Schedule> entry = schedulesMap.floorEntry(currentDay);
                                    if (entry == null)
                                        return Collections.emptySortedSet();

                                    Schedule currentSchedule = entry.getValue();

                                    // No patterns, or schedule ends before currentDay
                                    if (currentSchedule.getPatterns().isEmpty() || currentSchedule.getEnd().isBefore(currentDay))
                                        return Collections.emptySortedSet();
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
                                        int patternDuration = 0;
                                        while (cycleDaysToPass > 0) {
                                            SchedulePattern pattern = currentSchedule.getPatterns().get(patternIdx);
                                            patternDuration = pattern.getRepeatDays() + pattern.getPauseDays();
                                            cycleDaysToPass -= patternDuration;
                                            if (cycleDaysToPass >= 0)
                                                ++patternIdx;
                                        }
                                        // cycleDaysToPass will contain non-positive index of day after while loop
                                        // zero means first day of pattern
                                        // +1 as days count from 1, not 0
                                        int day = cycleDaysToPass < 0
                                            ? cycleDaysToPass + patternDuration + 1
                                            : cycleDaysToPass + 1 ;
                                        return currentSchedule.getPatterns().get(patternIdx).getReservationSlotsForDay(day);
                                    }
                                },
                                (existing, collision) -> existing,
                                TreeMap::new
                        )
                );
    }
}
