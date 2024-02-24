package net.kravuar.accounts.domain.commands;

public record AccountCreationCommand(
        String username,
        String firstName,
        String secondName,
        String email,
        String password
) {}
