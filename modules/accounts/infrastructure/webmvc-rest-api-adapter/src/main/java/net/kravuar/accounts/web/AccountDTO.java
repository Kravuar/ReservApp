package net.kravuar.accounts.web;

record AccountDTO(
        long id,
        String username,
        String email,
        boolean emailVerified
) {}
