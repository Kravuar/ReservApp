package net.kravuar.services.domain.commands;


public record ServiceCreationCommand(
        long businessId,
        String name
) {}
