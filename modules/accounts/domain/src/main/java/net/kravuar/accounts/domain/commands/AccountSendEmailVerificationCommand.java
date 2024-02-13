package net.kravuar.accounts.domain.commands;

public record AccountSendEmailVerificationCommand(
        long accountId
) {}
