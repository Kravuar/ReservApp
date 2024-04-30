package net.kravuar.business.dto;

public record BusinessCreationEventDTO(
    long businessId,
    String name,
    String ownerSub,
    boolean active
) {}
