package net.kravuar.integration.services;

public record ServiceCreationDTO(
        long id,
        long businessId,
        boolean active
) {
}
