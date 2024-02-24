package net.kravuar.business.domain.commands;

public record BusinessCreationCommand(
        String ownerSub,
        String name
) {}
