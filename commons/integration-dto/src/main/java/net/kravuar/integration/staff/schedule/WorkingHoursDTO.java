package net.kravuar.integration.staff.schedule;

import java.time.LocalTime;

public record WorkingHoursDTO(
        LocalTime start,
        LocalTime end,
        double cost
) {}
