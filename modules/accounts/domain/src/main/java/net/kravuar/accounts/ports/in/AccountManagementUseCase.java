package net.kravuar.accounts.ports.in;

import jakarta.validation.Valid;
import net.kravuar.accounts.domain.Account;
import net.kravuar.accounts.domain.commands.AccountChangeEmailCommand;
import net.kravuar.accounts.domain.commands.AccountCreationCommand;
import net.kravuar.accounts.domain.exceptions.AccountNotFoundException;
import net.kravuar.accounts.domain.exceptions.EmailAlreadyTakenException;
import net.kravuar.accounts.domain.exceptions.MessageSendingException;
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
    Account createAccount(@Valid AccountCreationCommand command);

    /**
     * Changes accounts email and sends verification message.
     *
     * @param command the command containing information of email to be verified
     * @throws AccountNotFoundException   if account wasn't found
     * @throws MessageSendingException    if an error occurs while sending a verification message
     * @throws EmailAlreadyTakenException if account with provided email already exists
     */
    void changeEmail(@Valid AccountChangeEmailCommand command) throws MessageSendingException;
}
