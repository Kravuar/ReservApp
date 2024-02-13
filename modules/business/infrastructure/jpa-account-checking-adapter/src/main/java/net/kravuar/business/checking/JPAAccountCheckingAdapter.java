package net.kravuar.business.checking;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.exceptions.AccountNotFoundException;
import net.kravuar.business.ports.out.AccountCheckingPort;
import org.springframework.stereotype.Component;

// TODO: Coupling between Account and Business, fine for monolith, though
// Hopefully it works at all
@Component
@RequiredArgsConstructor
class JPAAccountCheckingAdapter implements AccountCheckingPort {
    private final AccountEmailViewRepository accountRepository;

    @Override
    public boolean hasVerifiedEmail(long accountId) {
        return accountRepository
                .findOneById(accountId)
                .orElseThrow(AccountNotFoundException::new)
                .isEmailVerified();
    }
}
