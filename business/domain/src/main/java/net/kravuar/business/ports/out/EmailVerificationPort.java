package net.kravuar.business.ports.out;

import net.kravuar.business.domain.exceptions.MessageSendingException;

public interface EmailVerificationPort {
    /**
     * Sends email verification code.
     * @param email the email to send the code to
     * @throws MessageSendingException if the code failed to be sent to the provided email
     */
    void sendVerificationCode(String email) throws MessageSendingException;

    /**
     * Verifies email against provided verification code (sent by the {@link EmailVerificationPort#sendVerificationCode}).
     *
     * @param email email to verify
     * @param verificationCode code to verify against
     * @return {@code true} if verification succeeded, {@code false} otherwise
     */
    boolean verify(String email, String verificationCode);
}
