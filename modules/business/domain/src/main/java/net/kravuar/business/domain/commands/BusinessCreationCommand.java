package net.kravuar.business.domain.commands;

import jakarta.validation.constraints.Size;

public record BusinessCreationCommand(
        long ownerId,
        @Size(min = 3, max = 30) String name
) {}
