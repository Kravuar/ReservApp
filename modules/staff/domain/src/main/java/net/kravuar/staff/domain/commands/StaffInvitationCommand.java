package net.kravuar.staff.domain.commands;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StaffInvitationCommand(
        @NotNull
        @NotBlank
        String sub,
        long businessId
) {
}
