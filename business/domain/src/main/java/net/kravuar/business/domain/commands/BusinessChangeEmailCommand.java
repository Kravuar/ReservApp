package net.kravuar.business.domain.commands;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record BusinessChangeEmailCommand(
        long businessId,
        @Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$") String newEmail,
        @NotBlank String verificationCode
) {}
