package net.kravuar.staff.domain.commands;

import java.time.LocalDate;

public record ServiceScheduleRetrievalCommand(
        long serviceId,
        LocalDate startDate
) {}
