package net.kravuar.schedule.web;

import net.kravuar.staff.model.weak.ReservationSlot;

import java.time.LocalDate;
import java.util.SortedSet;

record ScheduleExceptionDayDTO(
        Long id,
        LocalDate date,
        StaffDTO staff,
        ServiceDTO service,
        SortedSet<ReservationSlot> reservationSlots
) {
}
