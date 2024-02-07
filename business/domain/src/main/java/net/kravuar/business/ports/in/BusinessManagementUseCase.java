package net.kravuar.business.ports.in;

import jakarta.validation.Valid;
import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.commands.BusinessCreationCommand;
import net.kravuar.business.domain.commands.BusinessChangeNameCommand;
import net.kravuar.business.domain.commands.BusinessChangeEmailCommand;
import net.kravuar.business.domain.commands.BusinessEmailVerificationCommand;
import net.kravuar.business.domain.exceptions.MessageSendingException;

public interface BusinessManagementUseCase {

    /**
     * Creates a new business.
     *
     * @param command the command containing information for creating the business
     * @return the created {@link Business}
     * @throws MessageSendingException if an error occurs while sending a message
     */
    Business create(@Valid BusinessCreationCommand command) throws MessageSendingException;

    /**
     * Changes the name of a {@link Business}.
     *
     * @param command the command containing information for changing the business name
     */
    void changeName(@Valid BusinessChangeNameCommand command);

    /**
     * Changes the email address of a {@link Business} and sends an email verification message.
     *
     * @param command the command containing information for changing the business email
     * @throws MessageSendingException if an error occurs while sending a message
     */
    void changeEmail(@Valid BusinessChangeEmailCommand command) throws MessageSendingException;

    /**
     * Verifies the email address of a business.
     *
     * @param command the command containing information for verifying the business email
     * @return {@code true} if the email is verified successfully, {@code false} otherwise
     */
    boolean verifyEmail(@Valid BusinessEmailVerificationCommand command);
}