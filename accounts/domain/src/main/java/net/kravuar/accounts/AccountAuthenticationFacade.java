package net.kravuar.accounts;

import lombok.RequiredArgsConstructor;
import net.kravuar.accounts.domain.Account;
import net.kravuar.accounts.domain.commands.AccountSignInCommand;
import net.kravuar.accounts.domain.commands.AccountSignUpCommand;
import net.kravuar.accounts.domain.exceptions.UsernameAlreadyTakenException;
import net.kravuar.accounts.ports.in.AccountAuthenticationUseCase;
import net.kravuar.accounts.ports.out.AccountPersistencePort;
import net.kravuar.accounts.ports.out.PasswordEncoderPort;

@RequiredArgsConstructor
public class AccountAuthenticationFacade implements AccountAuthenticationUseCase {
    private final AccountPersistencePort persistencePort;
    private final PasswordEncoderPort passwordEncoderPort;

    @Override
    public boolean signIn(AccountSignInCommand command) {
        Account account = persistencePort.findByUsername(command.username());
        String encodedPassword = passwordEncoderPort.encode(command.password());
        return account.getPasswordEncrypted().equals(encodedPassword);
    }

    @Override
    public Account signUp(AccountSignUpCommand command) throws UsernameAlreadyTakenException {
        Account newAccount = Account.builder()
                .username(command.username())
                .passwordEncrypted(passwordEncoderPort.encode(command.password()))
                .build();
        return persistencePort.save(newAccount);
    }
}
