package net.kravuar.accounts;

import lombok.RequiredArgsConstructor;
import net.kravuar.accounts.domain.Account;
import net.kravuar.accounts.domain.commands.AccountChangeEmailCommand;
import net.kravuar.accounts.domain.commands.AccountCreationCommand;
import net.kravuar.accounts.domain.exceptions.EmailAlreadyTakenException;
import net.kravuar.accounts.domain.exceptions.MessageSendingException;
import net.kravuar.accounts.domain.exceptions.UsernameAlreadyTakenException;
import net.kravuar.accounts.ports.in.AccountManagementUseCase;
import net.kravuar.accounts.ports.out.AccountPersistencePort;
import net.kravuar.accounts.ports.out.CodeGeneratorPort;
import net.kravuar.accounts.ports.out.EmailSendingPort;
import net.kravuar.accounts.ports.out.PasswordEncoderPort;
import net.kravuar.context.AppComponent;

@AppComponent
@RequiredArgsConstructor
public class AccountManagementFacade implements AccountManagementUseCase {
    private final AccountPersistencePort persistencePort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final EmailSendingPort emailSendingPort;
    private final CodeGeneratorPort codeGeneratorPort;

    @Override
    public Account createAccount(AccountCreationCommand command) {
        Account newAccount = Account.builder()
                .username(command.username())
                .email(command.email())
                .emailVerified(false)
                .passwordEncrypted(passwordEncoderPort.encode(command.password()))
                .build();
        // TODO: transaction for consistency
        if (persistencePort.existsByUsername(command.username()))
            throw new UsernameAlreadyTakenException();
        if (persistencePort.existsByEmail(command.email()))
            throw new EmailAlreadyTakenException();
        return persistencePort.save(newAccount);
    }

    @Override
    public void changeEmail(AccountChangeEmailCommand command) throws MessageSendingException {
        // TODO: transaction for consistency
        Account account = persistencePort.findById(command.accountId());
        if (persistencePort.existsByEmail(command.newEmail()))
            throw new EmailAlreadyTakenException();
        account.setEmail(command.newEmail());
        account.setEmailVerified(false);
        emailSendingPort.sendEmail(command.newEmail(), codeGeneratorPort.generate(account));
        persistencePort.save(account);
    }
}
