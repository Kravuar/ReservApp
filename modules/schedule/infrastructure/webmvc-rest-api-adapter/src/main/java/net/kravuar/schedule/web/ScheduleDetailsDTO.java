package net.kravuar.schedule.web;

import net.kravuar.staff.model.SchedulePattern;

import java.time.LocalDate;
import java.util.List;

record ScheduleDetailsDTO(
        LocalDate start,
        LocalDate end,
        List<SchedulePattern>patterns
) {
}
