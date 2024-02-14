package net.kravuar.accounts.persistence;

import lombok.RequiredArgsConstructor;
import net.kravuar.accounts.domain.Account;
import net.kravuar.accounts.domain.exceptions.AccountNotFoundException;
import net.kravuar.accounts.domain.exceptions.UsernameAlreadyTakenException;
import net.kravuar.accounts.ports.out.AccountPersistencePort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class JPAAccountPersistenceAdapter implements AccountPersistencePort {
    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;

    @Override
    public Account findById(long id) {
        return accountRepository
                .findById(id)
                .map(accountMapper::toDomain)
                .orElseThrow(AccountNotFoundException::new);
    }

    @Override
    public Account save(Account account) throws UsernameAlreadyTakenException {
        return accountMapper.toDomain(accountRepository
                .save(accountMapper.toModel(account))
        );
    }
}
