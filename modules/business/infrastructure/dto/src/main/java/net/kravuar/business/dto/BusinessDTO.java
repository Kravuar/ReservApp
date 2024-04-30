package net.kravuar.business.dto;

public record BusinessDTO(
        long id,
        String ownerSub,
        String name,
        boolean active,
        String description
) {
}
