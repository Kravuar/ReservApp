package net.kravuar.schedule.domain.commands;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import net.kravuar.schedule.domain.ReservationSlot;
import net.kravuar.schedule.domain.util.period.PeriodsNotIntersect;

import java.time.LocalDate;
import java.util.SortedSet;

public record CreateScheduleExceptionDayCommand(
        long staffId,
        long serviceId,
        @NotNull
        @FutureOrPresent
        LocalDate date,
        @NotNull
        @PeriodsNotIntersect
        SortedSet<@NotNull @Valid ReservationSlot> reservationSlots
) {
}