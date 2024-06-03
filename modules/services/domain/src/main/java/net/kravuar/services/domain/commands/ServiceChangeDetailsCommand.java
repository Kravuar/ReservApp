package net.kravuar.services.domain.commands;

import jakarta.validation.constraints.Size;

public record ServiceChangeDetailsCommand(
        long serviceId,
        @Size(min = 3, max = 30)
        String name,
        String description,
        String pictureUrl
        // TODO: picture and other details
) {
}
