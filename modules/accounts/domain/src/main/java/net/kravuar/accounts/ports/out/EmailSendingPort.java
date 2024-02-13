package net.kravuar.accounts.ports.out;

import net.kravuar.accounts.domain.exceptions.MessageSendingException;

public interface EmailSendingPort {
    /**
     * Sends email message.
     *
     * @param email   the email to send the message to
     * @param message the message contents
     * @throws MessageSendingException if the message failed to be sent to the provided email
     */
    void sendEmail(String email, String message) throws MessageSendingException;
}
