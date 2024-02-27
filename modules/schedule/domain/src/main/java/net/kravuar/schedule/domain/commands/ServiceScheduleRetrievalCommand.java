package net.kravuar.schedule.domain.commands;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ServiceScheduleRetrievalCommand(
        long serviceId,
        @FutureOrPresent
        @NotNull
        LocalDate startDate
) {
}
