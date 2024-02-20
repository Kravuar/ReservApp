package net.kravuar.services.domain.commands;


import jakarta.validation.constraints.Size;

public record ServiceCreationCommand(
        long businessId,
        @Size(min = 3, max = 30) String name
) {}
