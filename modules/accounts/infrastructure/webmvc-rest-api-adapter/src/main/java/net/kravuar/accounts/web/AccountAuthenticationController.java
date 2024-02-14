package net.kravuar.accounts.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.kravuar.accounts.domain.Account;
import net.kravuar.accounts.domain.commands.AccountSignInByEmailCommand;
import net.kravuar.accounts.domain.commands.AccountSignInByUsernameCommand;
import net.kravuar.accounts.ports.in.AccountAuthenticationUseCase;
import net.kravuar.security.AuthenticationHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static net.kravuar.accounts.web.Util.onAuth;

@RestController
@RequestMapping("/account/auth")
@RequiredArgsConstructor
class AccountAuthenticationController {
    private final AccountAuthenticationUseCase authentication;
    private final AuthenticationHandler authenticationHandler;
    private final AccountDTOMapper mapper;

    @GetMapping("/sign-in/username")
    AccountDTO signInUsername(@RequestBody AccountSignInByUsernameCommand command, HttpServletRequest request, HttpServletResponse response) {
        Account account = authentication.signInByUsername(command);
        onAuth(authenticationHandler, request, response, account);
        return mapper.toDto(account);
    }

    @GetMapping("/sign-in/email")
    AccountDTO signInEmail(@RequestBody AccountSignInByEmailCommand command, HttpServletRequest request, HttpServletResponse response) {
        Account account = authentication.signInByEmail(command);
        onAuth(authenticationHandler, request, response, account);
        return mapper.toDto(account);
    }

    @GetMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    boolean logOut(HttpServletRequest request, HttpServletResponse response) {
        return authenticationHandler.removeAuthentication(request, response);
    }
}
