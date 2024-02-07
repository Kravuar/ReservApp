package net.kravuar.business.ports.out;

import net.kravuar.business.domain.exceptions.MessageSendingException;

public interface EmailVerificationPort {
    /**
     * Sends email verification message.
     * @param email the email to send the message to
     * @throws MessageSendingException if the message failed to be sent to the provided email
     */
    void sendVerificationMessage(String email) throws MessageSendingException;

    /**
     * Verifies email against provided verification code (sent by the {@code sendVerificationMessage}).
     *
     * @param email email to verify
     * @param verificationCode code to verify against
     * @return {@code true} if verification succeeded, {@code false} otherwise
     */
    boolean verify(String email, String verificationCode);
}
