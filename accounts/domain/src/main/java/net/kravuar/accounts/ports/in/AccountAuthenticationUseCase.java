package net.kravuar.accounts.ports.in;

import jakarta.validation.Valid;
import net.kravuar.accounts.domain.Account;
import net.kravuar.accounts.domain.commands.AccountSignInCommand;
import net.kravuar.accounts.domain.commands.AccountSignUpCommand;
import net.kravuar.accounts.domain.exceptions.AccountNotFoundException;
import net.kravuar.accounts.domain.exceptions.UsernameAlreadyTakenException;

public interface AccountAuthenticationUseCase {
    /**
     * Executes sign-in process by verifying the provided credentials.
     *
     * @param command the command containing sign-in credentials
     * @return {@code true} if sign-in was successful, {@code false} otherwise
     * @throws AccountNotFoundException if the account wasn't found
     */
    boolean signIn(@Valid AccountSignInCommand command);

    /**
     * Creates a new account with the provided signup information.
     *
     * @param command the command containing account signup information
     * @return the newly created account
     * @throws UsernameAlreadyTakenException if an account with the provided username already exists
     */
    Account signUp(@Valid AccountSignUpCommand command) throws UsernameAlreadyTakenException;
}