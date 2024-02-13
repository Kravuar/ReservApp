package net.kravuar.accounts;

import lombok.RequiredArgsConstructor;
import net.kravuar.accounts.domain.Account;
import net.kravuar.accounts.domain.commands.AccountEmailVerificationCommand;
import net.kravuar.accounts.domain.commands.AccountSendEmailVerificationCommand;
import net.kravuar.accounts.domain.exceptions.EmailAlreadyVerifiedException;
import net.kravuar.accounts.domain.exceptions.MessageSendingException;
import net.kravuar.accounts.ports.in.AccountVerificationUseCase;
import net.kravuar.accounts.ports.out.AccountPersistencePort;
import net.kravuar.accounts.ports.out.CodeGeneratorPort;
import net.kravuar.accounts.ports.out.EmailSendingPort;
import net.kravuar.context.AppComponent;

@AppComponent
@RequiredArgsConstructor
public class AccountVerificationFacade implements AccountVerificationUseCase {
    private final AccountPersistencePort persistencePort;
    private final EmailSendingPort emailSendingPort;
    private final CodeGeneratorPort codeGeneratorPort;

    @Override
    public void sendEmailVerificationMessage(AccountSendEmailVerificationCommand command) throws MessageSendingException {
        Account account = persistencePort.findById(command.accountId());
        // TODO: transaction for consistency
        if (account.isEmailVerified())
            throw new EmailAlreadyVerifiedException();
        String code = codeGeneratorPort.generate(account);
        emailSendingPort.sendEmail(account.getEmail(), code);
    }

    @Override
    public boolean verifyEmail(AccountEmailVerificationCommand command) {
        Account account = persistencePort.findById(command.accountId());
        // TODO: transaction for consistency
        if (account.isEmailVerified())
            throw new EmailAlreadyVerifiedException();
        boolean valid = codeGeneratorPort.isValid(command.verificationCode(), account);
        if (valid)
            account.setEmailVerified(true);
        return valid;
    }
}
