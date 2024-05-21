package net.kravuar.schedule.web;

import net.kravuar.staff.model.weak.ReservationSlot;

import java.time.LocalDate;
import java.util.NavigableMap;
import java.util.SortedSet;

record ScheduleByServiceDTO(
        StaffDTO staff,
        NavigableMap<LocalDate, SortedSet<ReservationSlot>> schedule
) {
}
