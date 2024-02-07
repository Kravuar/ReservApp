package net.kravuar.business.domain.commands;

import jakarta.validation.constraints.NotBlank;

public record BusinessEmailVerificationCommand(
        long businessId,
        @NotBlank String verificationCode
) {}
