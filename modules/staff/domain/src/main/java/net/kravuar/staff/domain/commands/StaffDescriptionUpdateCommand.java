package net.kravuar.staff.domain.commands;

public record StaffDescriptionUpdateCommand(
        long staffId,
        String description
) {}
