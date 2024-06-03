package net.kravuar.business.web;

public record BusinessDTO(
        long id,
        String ownerSub,
        String name,
        boolean active,
        String description,
        String pictureUrl
) {
}
