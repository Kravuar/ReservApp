package net.kravuar.staff.domain.commands;


public record ServiceCreationCommand(
        long serviceId,
        long businessId,
        boolean active
) {}
