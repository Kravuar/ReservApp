package net.kravuar.accounts.ports.in;

import jakarta.validation.Valid;
import net.kravuar.accounts.domain.commands.AccountEmailVerificationCommand;
import net.kravuar.accounts.domain.commands.AccountSendEmailVerificationCommand;
import net.kravuar.accounts.domain.exceptions.AccountNotFoundException;
import net.kravuar.accounts.domain.exceptions.EmailAlreadyVerifiedException;
import net.kravuar.accounts.domain.exceptions.MessageSendingException;

public interface AccountVerificationUseCase {
    /**
     * Sends email verification message.
     *
     * @param command the command containing information of account which email is to be verified
     * @throws AccountNotFoundException      if account wasn't found
     * @throws MessageSendingException       if an error occurs while sending a verification message
     * @throws EmailAlreadyVerifiedException if email is already verified
     */
    void sendEmailVerificationMessage(@Valid AccountSendEmailVerificationCommand command) throws MessageSendingException;

    /**
     * Verifies email by provided code.
     *
     * @param command the command containing information for email verification
     * @return {@code true} if email successfully verified, {@code false} otherwise
     * @throws AccountNotFoundException      if account wasn't found
     * @throws EmailAlreadyVerifiedException if email is already verified
     */
    boolean verifyEmail(@Valid AccountEmailVerificationCommand command);
}