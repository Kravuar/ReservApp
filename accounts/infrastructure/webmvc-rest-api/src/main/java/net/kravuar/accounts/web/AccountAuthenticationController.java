package net.kravuar.accounts.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.accounts.domain.Account;
import net.kravuar.accounts.domain.commands.AccountSignInCommand;
import net.kravuar.accounts.domain.commands.AccountSignUpCommand;
import net.kravuar.accounts.ports.in.AccountAuthenticationUseCase;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account/auth")
@RequiredArgsConstructor
class AccountAuthenticationController {
    private final AccountAuthenticationUseCase authentication;

    // TODO: do i need dto's?

    @GetMapping("/sign-in")
    boolean signIn(@RequestBody AccountSignInCommand command) {
        // TODO: add JWT to response
        return authentication.signIn(command);
    }

    @PostMapping("/sign-up")
    Account signUp(@RequestBody AccountSignUpCommand command) {
        return authentication.signUp(command);
    }
}
