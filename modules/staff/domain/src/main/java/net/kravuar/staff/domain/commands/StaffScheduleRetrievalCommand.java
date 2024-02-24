package net.kravuar.staff.domain.commands;

import java.time.LocalDate;

public record StaffScheduleRetrievalCommand(
        long stuffId,
        long serviceId,
        LocalDate startDate
) {}
