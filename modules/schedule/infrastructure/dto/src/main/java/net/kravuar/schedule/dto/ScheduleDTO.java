package net.kravuar.schedule.dto;

import net.kravuar.schedule.model.SchedulePattern;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ScheduleDTO(
        Long id,
        LocalDate start,
        LocalDate end,
        StaffDTO staff,
        ServiceDTO service,
        List<SchedulePattern> patterns,
        LocalDateTime createdAt
) {
}
