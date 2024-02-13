package net.kravuar.accounts.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.accounts.domain.commands.AccountEmailVerificationCommand;
import net.kravuar.accounts.domain.commands.AccountSendEmailVerificationCommand;
import net.kravuar.accounts.domain.exceptions.MessageSendingException;
import net.kravuar.accounts.ports.in.AccountVerificationUseCase;
import net.kravuar.security.IdUserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account/verification")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
class AccountVerificationController {
    private final AccountVerificationUseCase verification;

    @PostMapping("/send-verification")
    void sendEmailVerificationMessage(@AuthenticationPrincipal IdUserDetails userDetails) throws MessageSendingException {
        var command = new AccountSendEmailVerificationCommand(
                userDetails.getId()
        );
        verification.sendEmailVerificationMessage(command);
    }

    @PostMapping("/verify-email")
    boolean verifyEmail(@AuthenticationPrincipal IdUserDetails userDetails, @RequestBody String code) {
        var command = new AccountEmailVerificationCommand(
                userDetails.getId(),
                code
        );
        return verification.verifyEmail(command);
    }
}
