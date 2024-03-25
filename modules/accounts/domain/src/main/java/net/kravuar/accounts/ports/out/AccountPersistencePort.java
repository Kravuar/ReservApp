package net.kravuar.accounts.ports.out;

import net.kravuar.accounts.domain.Account;
import net.kravuar.accounts.domain.exceptions.EmailAlreadyTakenException;
import net.kravuar.accounts.domain.exceptions.UsernameAlreadyTakenException;

public interface AccountPersistencePort {
    /**
     * Save account.
     *
     * @param account the account to save
     * @return the saved account object
     * @throws UsernameAlreadyTakenException if the username is already taken
     * @throws EmailAlreadyTakenException    if the email is already taken
     */
    Account save(Account account);
}
