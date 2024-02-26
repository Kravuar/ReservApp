
package net.kravuar.services.domain.commands;

public record HandleBusinessActiveChangeCommand(
        long businessId,
        boolean active
) {}
