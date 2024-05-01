package net.kravuar.schedule.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.kravuar.schedule.model.util.period.PeriodsNotIntersect;
import net.kravuar.schedule.model.weak.ReservationSlot;

import java.util.Collections;
import java.util.SortedSet;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SchedulePattern {
    private Long id;
    @Positive
    private int repeatDays;
    @PositiveOrZero
    private int pauseDays;
    @NotNull
    @PeriodsNotIntersect
    private SortedSet<@NotNull @Valid ReservationSlot> reservationSlots;

    public SortedSet<ReservationSlot> getReservationSlotsForDay(int day) {
        if (day < 1 || day > repeatDays + pauseDays)
            throw new IllegalArgumentException("Does not fall into pattern duration");
        if (day > repeatDays && pauseDays > 0)
            return Collections.emptySortedSet();
        return reservationSlots;
    }
}
