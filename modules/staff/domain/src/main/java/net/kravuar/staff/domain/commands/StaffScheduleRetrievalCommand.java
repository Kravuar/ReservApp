package net.kravuar.staff.domain.commands;

import java.time.LocalDate;

public record StaffScheduleRetrievalCommand(
        long staffId,
        long serviceId,
        LocalDate startDate
) {}
