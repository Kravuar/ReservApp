package net.kravuar.services.domain.commands;

import jakarta.validation.constraints.Size;

public record ServiceChangeNameCommand(
        long serviceId,
        @Size(min = 3, max = 30) String newName
) {}
