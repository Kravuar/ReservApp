package net.kravuar.staff.domain.commands;


public record BusinessCreationCommand(
        long businessId,
        String ownerSub,
        boolean active
) {}
