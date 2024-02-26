package net.kravuar.business.domain.commands;

public record BusinessChangeDetailsCommand(
        long businessId,
        String description
        // TODO: picture and other details
) {}
