package net.kravuar.business.domain.commands;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BusinessCreationCommand(
        @NotNull
        @NotBlank
        String ownerSub,
        @NotNull
        @Size(min = 3, max = 30)
        String name,
        String description
) {}
