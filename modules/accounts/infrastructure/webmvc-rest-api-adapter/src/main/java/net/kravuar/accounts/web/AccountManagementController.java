package net.kravuar.accounts.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.accounts.domain.Account;
import net.kravuar.accounts.domain.commands.AccountChangeEmailCommand;
import net.kravuar.accounts.domain.commands.AccountCreationCommand;
import net.kravuar.accounts.domain.exceptions.MessageSendingException;
import net.kravuar.accounts.ports.in.AccountManagementUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account/management")
@RequiredArgsConstructor
class AccountManagementController {
    private final AccountManagementUseCase management;
    private final AccountDTOMapper mapper;

    @GetMapping("/create")
    AccountDTO create(@RequestBody AccountCreationCommand command) {
        Account account = management.createAccount(command);
        return mapper.toDto(account);
    }

    @PutMapping("/change-email")
    @PreAuthorize("isAuthenticated()")
    void changeEmail(@AuthenticationPrincipal Jwt jwt, @RequestBody String email) throws MessageSendingException {
        var command = new AccountChangeEmailCommand(
                jwt.getSubject(),
                email
        );
        management.changeEmail(command);
    }
}
