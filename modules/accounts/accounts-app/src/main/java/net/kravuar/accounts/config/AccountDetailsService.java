package net.kravuar.accounts.config;

import lombok.RequiredArgsConstructor;
import net.kravuar.accounts.domain.Account;
import net.kravuar.accounts.ports.in.AccountRetrievalUseCase;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class AccountDetailsService implements UserDetailsService {
    private final AccountRetrievalUseCase retrievalUseCase;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = retrievalUseCase.findByUsername(username);
        return User
                .withUsername(account.getUsername())
                .password(account.getPasswordEncrypted())
                .build();
    }
}
