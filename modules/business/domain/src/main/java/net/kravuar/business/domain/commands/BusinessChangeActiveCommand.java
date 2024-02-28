package net.kravuar.business.domain.commands;

public record BusinessChangeActiveCommand(
        long businessId,
        boolean active
) {
}
