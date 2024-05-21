package net.kravuar.integration.services;

public record ServiceCreationDTO(
        long serviceId,
        long businessId,
        boolean active
) {
}
