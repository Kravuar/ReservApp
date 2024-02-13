package net.kravuar.accounts.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.kravuar.accounts.domain.Account;
import net.kravuar.security.AuthenticationHandler;
import net.kravuar.security.IdUserDetails;

import java.util.Collections;

class Util {
    static void onAuth(AuthenticationHandler handler, HttpServletRequest request, HttpServletResponse response, Account account) {
        IdUserDetails principal = new IdUserDetails(
                account.getId(),
                account.getUsername(),
                account.getPasswordEncrypted(),
                Collections.emptyList()
        );
        if (!handler.handleExistingAuthentication(request, response, principal))
            handler.handleAuthentication(request, response, principal);
    }
}
