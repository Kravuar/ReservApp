package net.kravuar.accounts.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.kravuar.accounts.domain.Account;
import net.kravuar.accounts.domain.commands.AccountChangeEmailCommand;
import net.kravuar.accounts.domain.commands.AccountCreationCommand;
import net.kravuar.accounts.domain.exceptions.MessageSendingException;
import net.kravuar.accounts.ports.in.AccountManagementUseCase;
import net.kravuar.security.AuthenticationHandler;
import net.kravuar.security.IdUserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static net.kravuar.accounts.web.Util.onAuth;

@RestController
@RequestMapping("/account/management")
@RequiredArgsConstructor
class AccountManagementController {
    private final AccountManagementUseCase management;
    private final AuthenticationHandler authenticationHandler;
    private final AccountDTOMapper mapper;

    @GetMapping("/create")
    AccountDTO create(@RequestBody AccountCreationCommand command, HttpServletRequest request, HttpServletResponse response) {
        Account account = management.createAccount(command);
        onAuth(authenticationHandler, request, response, account);
        return mapper.toDto(account);
    }

    @PutMapping("/change-email")
    @PreAuthorize("isAuthenticated()")
    void changeEmail(@AuthenticationPrincipal IdUserDetails userDetails, @RequestBody String email) throws MessageSendingException {
        var command = new AccountChangeEmailCommand(
                userDetails.getId(),
                email
        );
        management.changeEmail(command);
    }
}
