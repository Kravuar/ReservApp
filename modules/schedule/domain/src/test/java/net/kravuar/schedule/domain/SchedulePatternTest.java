package net.kravuar.schedule.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalTime;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SchedulePatternTest {
    static SortedSet<ReservationSlot> SLOTS = new TreeSet<>(Collections.singleton(
            new ReservationSlot(LocalTime.NOON, LocalTime.NOON.plusHours(1), 1, 1)
    ));
    static SchedulePattern PATTERN = new SchedulePattern(
            null,
            3,
            5,
            SLOTS
    );

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void getReservationSlotsForDayBeforePause_ThenReturnsSlots(int day) {
        assertThat(PATTERN.getReservationSlotsForDay(day))
                .isSameAs(SLOTS);
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 5, 6, 7, 8})
    void getReservationSlotsForDayAfterRepeat_ThenReturnsEmptySlots(int day) {
        assertThat(PATTERN.getReservationSlotsForDay(day))
                .isEmpty();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 9})
    void getReservationSlotsForOutOfBounds_ThenThrowsException(int day) {
        assertThatThrownBy(() -> PATTERN.getReservationSlotsForDay(day))
                .isInstanceOf(IllegalArgumentException.class);
    }
}