package net.kravuar.integration.staff;

public record StaffCreationDTO (
        long id,
        long businessId,
        boolean active
) {}
