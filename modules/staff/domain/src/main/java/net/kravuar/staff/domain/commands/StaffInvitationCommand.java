package net.kravuar.staff.domain.commands;

public record StaffInvitationCommand(
        String sub,
        long businessId
) {}
