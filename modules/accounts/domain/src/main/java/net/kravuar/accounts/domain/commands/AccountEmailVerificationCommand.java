package net.kravuar.accounts.domain.commands;

import jakarta.validation.constraints.NotBlank;

public record AccountEmailVerificationCommand(
        long accountId,
        @NotBlank String verificationCode
) {}
