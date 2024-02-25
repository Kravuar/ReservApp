
package net.kravuar.staff.domain.commands;

public record BusinessChangeActiveCommand(
        long businessId,
        boolean active
) {}
