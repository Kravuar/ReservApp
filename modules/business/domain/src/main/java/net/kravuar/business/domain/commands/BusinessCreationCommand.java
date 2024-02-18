package net.kravuar.business.domain.commands;

import jakarta.validation.constraints.Size;

public record BusinessCreationCommand(
        String ownerSub,
        @Size(min = 3, max = 30) String name
) {}
