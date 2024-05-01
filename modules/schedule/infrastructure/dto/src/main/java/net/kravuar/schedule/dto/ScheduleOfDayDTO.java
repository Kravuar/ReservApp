package net.kravuar.schedule.dto;

import net.kravuar.schedule.model.weak.ReservationSlot;

import java.time.LocalDate;
import java.util.List;

public record ScheduleOfDayDTO(
        LocalDate date,
        List<ReservationSlot> slots
) {}
