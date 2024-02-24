package net.kravuar.staff.domain.commands;

public record StaffAnswerInvitationCommand(
        String sub,
        long businessId,
        boolean accept
) {}
