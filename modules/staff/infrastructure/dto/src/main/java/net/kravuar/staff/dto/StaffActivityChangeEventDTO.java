package net.kravuar.staff.dto;

public record StaffActivityChangeEventDTO(
        long staffId,
        boolean active
) {}
