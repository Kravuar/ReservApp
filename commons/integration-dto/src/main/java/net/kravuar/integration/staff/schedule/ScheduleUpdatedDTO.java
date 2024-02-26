package net.kravuar.integration.staff.schedule;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public record ScheduleUpdatedDTO(
        DayOfWeek dayOfWeek,
        LocalDate validFrom,
        LocalDate validUntil,
        List<WorkingHoursDTO> workingHours
) {}
