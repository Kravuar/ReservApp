package net.kravuar.services.dto;

public record ServiceDTO(
        long id,
        BusinessDTO business,
        boolean active,
        String name,
        String description
) {
}
