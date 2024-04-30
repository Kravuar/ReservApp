package net.kravuar.business.dto;

public record BusinessActivityChangeEventDTO(
        long businessId,
        boolean active
) {}
