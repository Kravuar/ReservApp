package net.kravuar.accounts.ports.out;

import net.kravuar.accounts.domain.Account;
import net.kravuar.accounts.domain.exceptions.AccountNotFoundException;
import net.kravuar.accounts.domain.exceptions.EmailAlreadyTakenException;
import net.kravuar.accounts.domain.exceptions.UsernameAlreadyTakenException;

public interface AccountPersistencePort {
    /**
     * Find account by account ID.
     *
     * @param id the ID of the account to find
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

    /**
     * Find account by email.
     *
     * @param email email of the account to find
     * @return {@link Account} associated the with provided email
     * @throws AccountNotFoundException if account wasn't found
     */
    Account findByEmail(String email);

    /**
     * Check whether account exists by username.
     *
     * @param username the username to check
     * @return {@code true} if exists, {@code false} otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Check whether account exists by email.
     *
     * @param email the email to check
     * @return {@code true} if exists, {@code false} otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Save account.
     *
     * @param account the account to save
     * @return the saved account object
     * @throws UsernameAlreadyTakenException if the username is already taken
     * @throws EmailAlreadyTakenException if the email is already taken
     */
    Account save(Account account);
}
