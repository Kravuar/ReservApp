package net.kravuar.accounts.domain.commands;

import jakarta.validation.constraints.Email;

public record AccountChangeEmailCommand(
        long accountId,
        @Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$") String newEmail
) {}
