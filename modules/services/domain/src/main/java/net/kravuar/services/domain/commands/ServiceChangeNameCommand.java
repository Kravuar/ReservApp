package net.kravuar.services.domain.commands;

public record ServiceChangeNameCommand(
        long serviceId,
        String newName
) {}
