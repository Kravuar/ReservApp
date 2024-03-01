package net.kravuar.integration.services;

public record ServiceActivityChangeDTO(
        long serviceId,
        boolean active
) {}
