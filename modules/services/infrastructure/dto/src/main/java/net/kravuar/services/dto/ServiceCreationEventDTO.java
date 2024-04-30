package net.kravuar.services.dto;

public record ServiceCreationEventDTO(
        long serviceId,
        long businessId,
        boolean active
) {
}
