package net.kravuar.accounts.domain.commands;

import jakarta.validation.constraints.Size;

public record AccountSignInCommand(
        @Size(min = 3, max = 30) String username,
        @Size(min = 5, max = 30) String password
) {}
