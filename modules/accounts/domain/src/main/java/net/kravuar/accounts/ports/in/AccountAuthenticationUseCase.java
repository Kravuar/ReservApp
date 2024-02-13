package net.kravuar.accounts.ports.in;

import jakarta.validation.Valid;
import net.kravuar.accounts.domain.Account;
import net.kravuar.accounts.domain.commands.AccountSignInByEmailCommand;
import net.kravuar.accounts.domain.commands.AccountSignInByUsernameCommand;
import net.kravuar.accounts.domain.exceptions.AccountNotFoundException;
import net.kravuar.accounts.domain.exceptions.InvalidCredentialsException;

public interface AccountAuthenticationUseCase {
    /**
     * Executes sign-in by username process by verifying the provided credentials.
     *
     * @param command the command containing sign-in credentials
     * @return the authenticated account
     * @throws AccountNotFoundException if the account wasn't found
     * @throws InvalidCredentialsException if the credentials are invalid
     */
    Account signInByUsername(@Valid AccountSignInByUsernameCommand command);

    /**
     * Executes sign-in by email process by verifying the provided credentials.
     *
     * @param command the command containing sign-in credentials
     * @return the authenticated account
     * @throws AccountNotFoundException if the account wasn't found
     * @throws InvalidCredentialsException if the credentials are invalid
     */
    Account signInByEmail(@Valid AccountSignInByEmailCommand command);
}