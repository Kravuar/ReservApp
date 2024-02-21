package net.kravuar.integration.business;

public record BusinessCreationDTO (
    long id,
    String name,
    String ownerSub,
    boolean active
) {}
