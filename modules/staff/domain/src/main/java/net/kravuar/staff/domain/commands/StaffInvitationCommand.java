package net.kravuar.staff.domain.commands;

import jakarta.validation.constraints.NotBlank;

public record StaffInvitationCommand(
        @NotBlank
        String sub,
        long businessId,
        String description
) {}
