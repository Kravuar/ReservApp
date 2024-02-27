package net.kravuar.business.web;

public record BusinessDTO(
        Long id,
        String ownerSub,
        String name,
        boolean active,
        String description
) {
}
