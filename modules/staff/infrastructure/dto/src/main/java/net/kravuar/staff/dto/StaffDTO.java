package net.kravuar.staff.dto;

public record StaffDTO(
        Long id,
        String sub,
        String name,
        String description,
//        URI picture,
        BusinessDTO business,
        boolean active
) {
}
