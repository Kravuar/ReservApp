package net.kravuar.services.domain.commands;

public record ServiceChangeActiveCommand(
        long serviceId,
        boolean active
) {
}
