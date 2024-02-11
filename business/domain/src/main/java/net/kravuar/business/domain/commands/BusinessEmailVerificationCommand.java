package net.kravuar.business.domain.commands;

import jakarta.validation.constraints.Email;

public record BusinessEmailVerificationCommand(
        @Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$") String email
) {}
