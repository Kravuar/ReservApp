package net.kravuar.schedule.web;

import java.time.LocalDate;

record ScheduleDurationDTO(
        LocalDate start,
        LocalDate end
) {
}
