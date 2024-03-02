package net.kravuar.schedule.web;

import net.kravuar.schedule.domain.ScheduleExceptionDay;
import net.kravuar.schedule.domain.SchedulePattern;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

record ScheduleDTO(
        Long id,
        LocalDate start,
        LocalDate end,
        StaffDTO staff,
        ServiceDTO service,
        List<SchedulePattern> patterns,
        List<ScheduleExceptionDay> exceptionDays,
        LocalDateTime createdAt,
        boolean active
) {}
