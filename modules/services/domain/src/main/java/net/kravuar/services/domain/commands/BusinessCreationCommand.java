package net.kravuar.services.domain.commands;


public record BusinessCreationCommand(
        long businessId,
        String ownerSub,
        boolean active
) {}
