package net.kravuar.accounts;

import lombok.RequiredArgsConstructor;
import net.kravuar.accounts.domain.Account;
import net.kravuar.accounts.domain.commands.AccountChangeEmailCommand;
import net.kravuar.accounts.domain.commands.AccountCreationCommand;
import net.kravuar.accounts.domain.exceptions.MessageSendingException;
import net.kravuar.accounts.ports.in.AccountManagementUseCase;
import net.kravuar.accounts.ports.out.*;
import net.kravuar.context.AppComponent;

@AppComponent
@RequiredArgsConstructor
public class AccountManagementFacade implements AccountManagementUseCase {
    private final AccountPersistencePort persistencePort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final EmailSendingPort emailSendingPort;
    private final CodeGeneratorPort codeGeneratorPort;
    private final NotificationPort notificationPort;

    @Override
    public Account createAccount(AccountCreationCommand command) {
        Account created = persistencePort.save(Account.builder()
                .username(command.username())
                .email(command.email())
                .emailVerified(false)
                .passwordEncrypted(passwordEncoderPort.encode(command.password()))
                .build());
        notificationPort.onAccountCreation(created.getId(), created.getUsername(), created.getEmail(), false);
        return created;
    }

    @Override
    public void changeEmail(AccountChangeEmailCommand command) throws MessageSendingException {
        Account account = persistencePort.findByUsername(command.username());

        account.setEmail(command.newEmail());
        account.setEmailVerified(false);
        persistencePort.save(account);

        notificationPort.onEmailVerifiedChange(account.getId(), false);
        emailSendingPort.sendEmail(command.newEmail(), codeGeneratorPort.generate(account));
    }
}
