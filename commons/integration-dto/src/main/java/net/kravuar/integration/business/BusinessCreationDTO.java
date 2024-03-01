package net.kravuar.integration.business;

public record BusinessCreationDTO (
    long businessId,
    String name,
    String ownerSub,
    boolean active
) {}
