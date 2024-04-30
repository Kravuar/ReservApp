package net.kravuar.schedule.domain;

import net.kravuar.staff.model.Schedule;
import net.kravuar.staff.model.ScheduleExceptionDay;
import net.kravuar.staff.model.SchedulePattern;
import net.kravuar.staff.model.weak.ReservationSlot;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class ScheduleTest {
    static final int YEAR = 2024;

    static ReservationSlot NINE = new ReservationSlot(LocalTime.of(9, 0), LocalTime.of(10, 0), 1, 1);
    static ReservationSlot TEN = new ReservationSlot(LocalTime.of(10, 0), LocalTime.of(11, 0), 1, 1);
    static ReservationSlot ELEVEN = new ReservationSlot(LocalTime.of(11, 0), LocalTime.of(12, 0), 1, 1);
    static ReservationSlot TWELVE = new ReservationSlot(LocalTime.of(12, 0), LocalTime.of(13, 0), 1, 1);

    static Schedule threeTwoThenFiveTwo(LocalDate start, LocalDate end, ReservationSlot... slots) {
        return new Schedule(null,
                start,
                end,
                null,
                null,
                List.of(
                        new SchedulePattern(null, 3, 2, new TreeSet<>(Set.of(slots))),
                        new SchedulePattern(null, 5, 2, new TreeSet<>(Set.of(slots)))
                        ),
                true
        );
    }

    static Schedule twoTwo(LocalDate start, LocalDate end, ReservationSlot... slots) {
        return new Schedule(null,
                start,
                end,
                null,
                null,
                List.of(new SchedulePattern(null, 2, 2, new TreeSet<>(Set.of(slots)))),
                true
        );
    }

    static ScheduleExceptionDay exceptionDay(LocalDate date, ReservationSlot... slots) {
        return new ScheduleExceptionDay(
                null,
                date,
                null,
                null,
                new TreeSet<>(Set.of(slots))
        );
    }

    @Test
    void givenSchedulesAndExceptionDays_whenAsPerDay_thenCorrectReservationSlotsReturned() {
        // Given
        List<Schedule> schedules = List.of(
                // 3/2, then 5/2, from 1st to 16th with slots at 9, 10 and 11
                threeTwoThenFiveTwo(LocalDate.of(YEAR, 1, 1), LocalDate.of(YEAR, 1, 16), NINE, TEN, ELEVEN),
                // 2/2, from 20th to 28th with slots at 10, 11 and 12
                twoTwo(LocalDate.of(YEAR, 1, 20), LocalDate.of(YEAR, 1, 28), TEN, ELEVEN, TWELVE),
                // 3/2, then 5/2, for a month with slots at 9, 10 and 11
                threeTwoThenFiveTwo(LocalDate.of(YEAR, 2, 1), LocalDate.of(YEAR, 3, 1), NINE, TEN, ELEVEN)
        );

        Map<LocalDate, ScheduleExceptionDay> exceptionDays = new HashMap<>();
        LocalDate exceptionDate1 = LocalDate.of(YEAR, 1, 13); // first schedule
        exceptionDays.put(exceptionDate1, exceptionDay(exceptionDate1, NINE));
        LocalDate exceptionDate2 = LocalDate.of(YEAR, 1, 19); // between first and second
        exceptionDays.put(exceptionDate2, exceptionDay(exceptionDate2, NINE));
        LocalDate exceptionDate3 = LocalDate.of(YEAR, 1, 25); // second schedule
        exceptionDays.put(exceptionDate3, exceptionDay(exceptionDate3, NINE));

        // Whole schedules range
        LocalDate from = LocalDate.of(YEAR, 1, 1);
        LocalDate to = LocalDate.of(YEAR, 3, 1);

        // When
        NavigableMap<LocalDate, SortedSet<ReservationSlot>> result = Schedule.asPerDay(schedules, exceptionDays, from, to);

        // Then
        // First schedule bound cases
        assertThat(result.get(LocalDate.of(YEAR, 1, 1))).containsExactly(NINE, TEN, ELEVEN);
        assertThat(result.get(LocalDate.of(YEAR, 1, 3))).containsExactly(NINE, TEN, ELEVEN);
        assertThat(result.get(LocalDate.of(YEAR, 1, 4))).isEmpty();
        assertThat(result.get(LocalDate.of(YEAR, 1, 5))).isEmpty();
        assertThat(result.get(LocalDate.of(YEAR, 1, 6))).containsExactly(NINE, TEN, ELEVEN);
        assertThat(result.get(LocalDate.of(YEAR, 1, 10))).containsExactly(NINE, TEN, ELEVEN);
        assertThat(result.get(LocalDate.of(YEAR, 1, 11))).isEmpty();
        assertThat(result.get(LocalDate.of(YEAR, 1, 12))).isEmpty();
        // Exception day overriding slots
        assertThat(result.get(exceptionDate1)).containsExactly(NINE);
        
        // Exception day overriding empty slots
        assertThat(result.get(exceptionDate2)).containsExactly(NINE);
        
        // Schedule 2
        assertThat(result.get(LocalDate.of(YEAR, 1, 20))).containsExactly(TEN, ELEVEN, TWELVE);
        assertThat(result.get(LocalDate.of(YEAR, 1, 21))).containsExactly(TEN, ELEVEN, TWELVE);
        assertThat(result.get(LocalDate.of(YEAR, 1, 22))).isEmpty();
        assertThat(result.get(LocalDate.of(YEAR, 1, 23))).isEmpty();
        assertThat(result.get(LocalDate.of(YEAR, 1, 24))).containsExactly(TEN, ELEVEN, TWELVE);
        // Exception day overriding slots
        assertThat(result.get(exceptionDate3)).containsExactly(NINE);

        // Schedule 3
        assertThat(result.get(LocalDate.of(YEAR, 2, 1))).containsExactly(NINE, TEN, ELEVEN);
        assertThat(result.get(LocalDate.of(YEAR, 2, 3))).containsExactly(NINE, TEN, ELEVEN);
        assertThat(result.get(LocalDate.of(YEAR, 2, 4))).isEmpty();
        assertThat(result.get(LocalDate.of(YEAR, 2, 5))).isEmpty();
        assertThat(result.get(LocalDate.of(YEAR, 2, 28))).isEmpty();
        assertThat(result.get(LocalDate.of(YEAR, 2, 29))).isEmpty();
        assertThat(result.get(LocalDate.of(YEAR, 3, 1))).containsExactly(NINE, TEN, ELEVEN);
    }

    @Test
    void givenSchedulesAndNoExceptionDays_whenAsPerDay_thenCorrectReservationSlotsReturned() {
        // Given
        List<Schedule> schedules = List.of(
                // 3/2, then 5/2, from 1st to 16th with slots at 9, 10 and 11
                threeTwoThenFiveTwo(LocalDate.of(YEAR, 1, 1), LocalDate.of(YEAR, 1, 16), NINE, TEN, ELEVEN),
                // 2/2, from 20th to 28th with slots at 10, 11 and 12
                twoTwo(LocalDate.of(YEAR, 1, 20), LocalDate.of(YEAR, 1, 28), TEN, ELEVEN, TWELVE),
                // 3/2, then 5/2, for a month with slots at 9, 10 and 11
                threeTwoThenFiveTwo(LocalDate.of(YEAR, 2, 1), LocalDate.of(YEAR, 3, 1), NINE, TEN, ELEVEN)
        );

        // Whole schedules range
        LocalDate from = LocalDate.of(YEAR, 1, 1);
        LocalDate to = LocalDate.of(YEAR, 3, 1);

        // When
        NavigableMap<LocalDate, SortedSet<ReservationSlot>> result = Schedule.asPerDay(schedules, Collections.emptyMap(), from, to);

        // Then
        // First schedule bound cases
        // Not an exception day
        assertThat(result.get(LocalDate.of(YEAR, 1, 13))).containsExactly(NINE, TEN, ELEVEN);

        // Not an exception day
        assertThat(result.get(LocalDate.of(YEAR, 1, 19))).isEmpty();

        // Schedule 2
        // Not an exception day
        assertThat(result.get(LocalDate.of(YEAR, 1, 25))).containsExactly(TEN, ELEVEN, TWELVE);
    }

    @Test
    void givenNoSchedulesAndExceptionDays_whenAsPerDay_thenCorrectReservationSlotsReturned() {
        // Given
        Map<LocalDate, ScheduleExceptionDay> exceptionDays = new HashMap<>();
        LocalDate exceptionDate1 = LocalDate.of(YEAR, 1, 13);
        exceptionDays.put(exceptionDate1, exceptionDay(exceptionDate1, NINE));
        LocalDate exceptionDate2 = LocalDate.of(YEAR, 1, 19);
        exceptionDays.put(exceptionDate2, exceptionDay(exceptionDate2, NINE));
        LocalDate exceptionDate3 = LocalDate.of(YEAR, 1, 25);
        exceptionDays.put(exceptionDate3, exceptionDay(exceptionDate3, NINE));

        // Whole schedules range
        LocalDate from = LocalDate.of(YEAR, 1, 1);
        LocalDate to = LocalDate.of(YEAR, 3, 1);

        // When
        NavigableMap<LocalDate, SortedSet<ReservationSlot>> result = Schedule.asPerDay(Collections.emptyList(), exceptionDays, from, to);

        // Then
        // Exception days works without schedule
        assertThat(result.get(exceptionDate1)).containsExactly(NINE);
        assertThat(result.get(exceptionDate2)).containsExactly(NINE);
        assertThat(result.get(exceptionDate3)).containsExactly(NINE);
    }
}