package net.kravuar.business.domain.commands;

public record BusinessChangeDetailsCommand(
        long businessId,
        String description,
        String pictureUrl
        // TODO: picture and other details
) {
}
