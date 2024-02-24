package net.kravuar.staff.domain.commands;

public record StaffRemovalCommand(
        String sub,
        long businessId
) {}
