package net.kravuar.staff.domain.commands;

public record StaffChangeDetailsCommand(
        long staffId,
        String description
        // TODO: picture and other details
) {
}
