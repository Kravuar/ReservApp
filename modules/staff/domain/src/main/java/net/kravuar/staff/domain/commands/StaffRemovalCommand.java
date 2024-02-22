package net.kravuar.staff.domain.commands;

import jakarta.validation.constraints.NotBlank;

public record StaffRemovalCommand(
        @NotBlank
        String sub,
        long businessId
) {}
