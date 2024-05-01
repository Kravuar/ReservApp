package net.kravuar.schedule.dto;

import net.kravuar.schedule.model.weak.ReservationSlot;

import java.time.LocalDate;
import java.util.SortedSet;

public record ScheduleExceptionDayCreationDTO(
        LocalDate date,
        SortedSet<ReservationSlot> reservationSlots
) {
}
