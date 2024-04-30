package net.kravuar.schedule.dto;

import net.kravuar.staff.model.SchedulePattern;

import java.time.LocalDate;
import java.util.List;

public record ScheduleDetailsDTO(
        LocalDate start,
        LocalDate end,
        List<SchedulePattern>patterns
) {
}
