package net.kravuar.accounts;

import lombok.RequiredArgsConstructor;
import net.kravuar.accounts.domain.Account;
import net.kravuar.accounts.ports.in.AccountRetrievalUseCase;
import net.kravuar.accounts.ports.out.AccountPersistencePort;
import net.kravuar.context.AppComponent;

@AppComponent
@RequiredArgsConstructor
public class AccountRetrievalFacade implements AccountRetrievalUseCase {
    private final AccountPersistencePort persistencePort;

    @Override
    public Account findById(long id) {
        return persistencePort.findById(id);
    }

    @Override
    public Account findByUsername(String username) {
        return persistencePort.findByUsername(username);
    }
}
