
package net.kravuar.staff.domain.commands;

public record ServiceChangeActiveCommand(
        long serviceId,
        boolean active
) {}
