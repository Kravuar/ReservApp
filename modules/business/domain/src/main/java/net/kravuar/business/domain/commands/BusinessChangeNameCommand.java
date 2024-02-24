package net.kravuar.business.domain.commands;

public record BusinessChangeNameCommand(
        long businessId,
        String newName
) {}
