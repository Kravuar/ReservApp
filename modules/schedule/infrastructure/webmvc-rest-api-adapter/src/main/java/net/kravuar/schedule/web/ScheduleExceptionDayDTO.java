package net.kravuar.schedule.web;

import net.kravuar.schedule.domain.weak.WorkingHours;

import java.time.LocalDate;
import java.util.List;

record ScheduleExceptionDayDTO(
        Long id,
        LocalDate date,
        StaffDTO staff,
        ServiceDTO service,
        List<WorkingHours> workingHours
) {
}
