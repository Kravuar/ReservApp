package net.kravuar.staff.domain.commands;

import jakarta.validation.constraints.NotBlank;

public record StaffAnswerInvitationCommand(
        @NotBlank
        String sub,
        long businessId,
        boolean accept
) {}
