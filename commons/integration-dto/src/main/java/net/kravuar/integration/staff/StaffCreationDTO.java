package net.kravuar.integration.staff;

public record StaffCreationDTO (
        long staffId,
        long businessId,
        String sub,
        boolean active
) {}
