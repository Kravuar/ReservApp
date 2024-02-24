package net.kravuar.staff.domain.commands;

public record StaffAnswerInvitationCommand(
        long invitationId,
        boolean accept
) {}
