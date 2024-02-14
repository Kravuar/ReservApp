package net.kravuar.accounts.domain.commands;

import jakarta.validation.constraints.NotBlank;

public record AccountEmailVerificationCommand(
        String username,
        @NotBlank String verificationCode
) {}
