package net.kravuar.accounts.ports.in;

import jakarta.validation.Valid;
import net.kravuar.accounts.domain.commands.AccountCreationCommand;
import net.kravuar.accounts.domain.exceptions.EmailAlreadyTakenException;
import net.kravuar.accounts.domain.exceptions.UsernameAlreadyTakenException;
import net.kravuar.accounts.model.Account;
import net.kravuar.context.AppValidated;

@AppValidated
public interface AccountManagementUseCase {
    /**
     * Creates a new account with the provided information.
     *
     * @param command the command containing account information
     * @return the newly created account
     * @throws UsernameAlreadyTakenException if an account with the provided {@code username} already exists
     * @throws EmailAlreadyTakenException    if an account with the provided {@code email} already exists
     */
    Account createAccount(@Valid AccountCreationCommand command);
}
