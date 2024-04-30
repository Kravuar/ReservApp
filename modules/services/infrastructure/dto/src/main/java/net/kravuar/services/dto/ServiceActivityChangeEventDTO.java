package net.kravuar.services.dto;

public record ServiceActivityChangeEventDTO(
        long serviceId,
        boolean active
) {}
