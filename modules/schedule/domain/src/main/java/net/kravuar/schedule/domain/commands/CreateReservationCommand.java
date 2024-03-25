package net.kravuar.schedule.domain.commands;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateReservationCommand(
        @NotNull
        @NotBlank
        String sub,
        long staffId,
        long serviceId,
        @NotNull
        @FutureOrPresent
        LocalDateTime dateTime
) {
}