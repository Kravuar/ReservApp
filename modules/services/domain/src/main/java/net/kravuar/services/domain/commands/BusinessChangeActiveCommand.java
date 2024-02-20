
package net.kravuar.services.domain.commands;

public record BusinessChangeActiveCommand(
        long businessId,
        boolean active
) {}
