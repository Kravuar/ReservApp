package net.kravuar.accounts.notifier;

record CreateDTO(long accountId, String username, String email, boolean emailVerified) {}
