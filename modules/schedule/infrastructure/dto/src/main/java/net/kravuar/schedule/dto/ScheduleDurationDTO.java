package net.kravuar.schedule.dto;

import java.time.LocalDate;

public record ScheduleDurationDTO(
        LocalDate start,
        LocalDate end
) {
}
