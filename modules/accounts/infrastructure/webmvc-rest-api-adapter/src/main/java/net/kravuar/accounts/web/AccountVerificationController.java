package net.kravuar.accounts.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.accounts.domain.commands.AccountEmailVerificationCommand;
import net.kravuar.accounts.domain.commands.AccountSendEmailVerificationCommand;
import net.kravuar.accounts.domain.exceptions.MessageSendingException;
import net.kravuar.accounts.ports.in.AccountVerificationUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/verification")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
class AccountVerificationController {
    private final AccountVerificationUseCase verification;

    @PostMapping("/send-verification")
    void sendEmailVerificationMessage(@AuthenticationPrincipal Jwt jwt) throws MessageSendingException {
        var command = new AccountSendEmailVerificationCommand(
                jwt.getSubject()
        );
        verification.sendEmailVerificationMessage(command);
    }

    @PostMapping("/verify-email/{code}")
    boolean verifyEmail(@AuthenticationPrincipal Jwt jwt, @PathVariable("code") String code) {
        var command = new AccountEmailVerificationCommand(
                jwt.getSubject(),
                code
        );
        return verification.verifyEmail(command);
    }
}
