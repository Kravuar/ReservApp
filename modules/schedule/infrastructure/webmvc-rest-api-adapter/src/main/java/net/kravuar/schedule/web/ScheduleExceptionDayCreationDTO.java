package net.kravuar.schedule.web;

import net.kravuar.staff.model.weak.ReservationSlot;

import java.time.LocalDate;
import java.util.SortedSet;

record ScheduleExceptionDayCreationDTO(
        LocalDate date,
        SortedSet<ReservationSlot> reservationSlots
) {
}
