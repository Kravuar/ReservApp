package net.kravuar.accounts.ports.in;

import net.kravuar.accounts.domain.Account;
import net.kravuar.accounts.domain.commands.AccountCreationCommand;
import net.kravuar.accounts.domain.exceptions.EmailAlreadyTakenException;
import net.kravuar.accounts.domain.exceptions.UsernameAlreadyTakenException;

public interface AccountManagementUseCase {
    /**
     * Creates a new account with the provided information.
     *
     * @param command the command containing account information
     * @return the newly created account
     * @throws UsernameAlreadyTakenException if an account with the provided username already exists
     * @throws EmailAlreadyTakenException if an account with the provided email already exists
     */
    Account createAccount(AccountCreationCommand command);
}
