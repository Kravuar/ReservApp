package net.kravuar.staff.domain.commands;

public record StaffDescriptionUpdateCommand(
        String sub,
        String description
) {}
