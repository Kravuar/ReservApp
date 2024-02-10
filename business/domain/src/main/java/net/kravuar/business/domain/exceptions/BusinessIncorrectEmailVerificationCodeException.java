package net.kravuar.business.domain.exceptions;

public class BusinessIncorrectEmailVerificationCodeException extends RuntimeException {
    public BusinessIncorrectEmailVerificationCodeException() {
        super("Incorrect verification code");
    }
}
