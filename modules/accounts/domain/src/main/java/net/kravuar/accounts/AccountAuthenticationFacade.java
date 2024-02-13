package net.kravuar.accounts;

import lombok.RequiredArgsConstructor;
import net.kravuar.accounts.domain.Account;
import net.kravuar.accounts.domain.commands.AccountSignInByEmailCommand;
import net.kravuar.accounts.domain.commands.AccountSignInByUsernameCommand;
import net.kravuar.accounts.domain.exceptions.InvalidCredentialsException;
import net.kravuar.accounts.ports.in.AccountAuthenticationUseCase;
import net.kravuar.accounts.ports.out.AccountPersistencePort;
import net.kravuar.accounts.ports.out.PasswordEncoderPort;
import net.kravuar.context.AppComponent;

@AppComponent
@RequiredArgsConstructor
public class AccountAuthenticationFacade implements AccountAuthenticationUseCase {
    private final AccountPersistencePort persistencePort;
    private final PasswordEncoderPort passwordEncoderPort;

    @Override
    public Account signInByUsername(AccountSignInByUsernameCommand command) {
        Account account = persistencePort.findByUsername(command.username());
        String encodedPassword = passwordEncoderPort.encode(command.password());
        if (!account.getPasswordEncrypted().equals(encodedPassword))
            throw new InvalidCredentialsException();
        return account;
    }

    @Override
    public Account signInByEmail(AccountSignInByEmailCommand command) {
        Account account = persistencePort.findByEmail(command.email());
        String encodedPassword = passwordEncoderPort.encode(command.password());
        if (!account.getPasswordEncrypted().equals(encodedPassword))
            throw new InvalidCredentialsException();
        return account;
    }
}
