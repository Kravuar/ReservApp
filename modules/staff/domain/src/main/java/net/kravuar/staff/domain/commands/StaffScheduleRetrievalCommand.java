package net.kravuar.staff.domain.commands;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record StaffScheduleRetrievalCommand(
        long stuffId,
        long serviceId,
        @NotNull
        @FutureOrPresent
        LocalDate startDate
) {}
