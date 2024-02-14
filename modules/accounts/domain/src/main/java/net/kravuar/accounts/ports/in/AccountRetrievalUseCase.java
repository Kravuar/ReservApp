package net.kravuar.accounts.ports.in;

import net.kravuar.accounts.domain.Account;
import net.kravuar.accounts.domain.exceptions.AccountNotFoundException;

public interface AccountRetrievalUseCase {
    /**
     * Find account by account ID.
     *
     * @param id the id of the account to find
     * @return the account associated with the provided ID
     * @throws AccountNotFoundException if the account wasn't found
     */
    Account findById(long id);

    /**
     * Find account by username.
     *
     * @param username the username of the account to find
     * @return the account associated with the provided username
     * @throws AccountNotFoundException if the account wasn't found
     */
    Account findByUsername(String username);
}
