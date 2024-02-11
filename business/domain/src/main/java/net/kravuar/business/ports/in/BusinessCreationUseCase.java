package net.kravuar.business.ports.in;

import jakarta.validation.Valid;
import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.commands.BusinessCreationCommand;
import net.kravuar.business.domain.commands.BusinessEmailVerificationCommand;
import net.kravuar.business.domain.exceptions.BusinessWithEmailAlreadyExistsException;
import net.kravuar.business.domain.exceptions.MessageSendingException;

public interface BusinessCreationUseCase {
    // TODO: Encapsulate the regex of @Email annotation in custom annotation

    /**
     * Begins business creation process (or retries) by sending email verification message.
     *
     * @param command the command containing information of email to be verified
     * @throws MessageSendingException                 if an error occurs while sending a message
     * @throws BusinessWithEmailAlreadyExistsException if business with provided email already exists
     */
    void sendVerification(@Valid BusinessEmailVerificationCommand command) throws MessageSendingException;

    /**
     * Finishes creation of a {@link Business} if email verification code is correct.
     *
     * @param command the command containing information for verifying the business email
     * @return Newly created {@link Business}
     * @throws BusinessWithEmailAlreadyExistsException if business with provided email already exists
     * @throws IllegalArgumentException                if verification code isn't correct
     */
    Business create(@Valid BusinessCreationCommand command);
}