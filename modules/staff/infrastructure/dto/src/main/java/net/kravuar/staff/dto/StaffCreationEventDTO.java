package net.kravuar.staff.dto;

public record StaffCreationEventDTO(
        long staffId,
        long businessId,
        String sub,
        boolean active
) {}
