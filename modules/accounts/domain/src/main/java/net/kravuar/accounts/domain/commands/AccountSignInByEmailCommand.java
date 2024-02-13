package net.kravuar.accounts.domain.commands;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record AccountSignInByEmailCommand(
        @Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$") String email,
        @Size(min = 5, max = 30) String password
) {}
