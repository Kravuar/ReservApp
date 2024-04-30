package net.kravuar.schedule.dto;

import net.kravuar.staff.model.weak.ReservationSlot;

import java.time.LocalDate;
import java.util.NavigableMap;
import java.util.SortedSet;

public record ScheduleByServiceDTO(
        StaffDTO staff,
        NavigableMap<LocalDate, SortedSet<ReservationSlot>> schedule
) {
}
