package net.kravuar.services.domain.commands;

public record ServiceChangeDetailsCommand(
        long serviceId,
        String description
        // TODO: picture and other details
) {
}
