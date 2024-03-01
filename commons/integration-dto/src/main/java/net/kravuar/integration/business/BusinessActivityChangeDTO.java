package net.kravuar.integration.business;

public record BusinessActivityChangeDTO (
        long businessId,
        boolean active
) {}
