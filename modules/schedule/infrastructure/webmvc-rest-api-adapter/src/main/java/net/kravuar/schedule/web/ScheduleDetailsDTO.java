package net.kravuar.schedule.web;

import net.kravuar.schedule.domain.SchedulePattern;

import java.time.LocalDate;
import java.util.List;

record ScheduleDetailsDTO(
        LocalDate start,
        LocalDate end,
        List<SchedulePattern>patterns
) {
}
