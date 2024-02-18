package net.kravuar.accounts;

import lombok.RequiredArgsConstructor;
import net.kravuar.accounts.domain.Account;
import net.kravuar.accounts.domain.commands.AccountCreationCommand;
import net.kravuar.accounts.ports.in.AccountManagementUseCase;
import net.kravuar.accounts.ports.out.AccountPersistencePort;
import net.kravuar.context.AppComponent;

@AppComponent
@RequiredArgsConstructor
public class AccountManagementFacade implements AccountManagementUseCase {
    private final AccountPersistencePort persistencePort;

    @Override
    public Account createAccount(AccountCreationCommand command) {
        return persistencePort.save(Account.builder()
                .username(command.username())
                .firstName(command.firstName())
                .lastName(command.secondName())
                .email(command.email())
                .password(command.password())
                .build());
    }
}
