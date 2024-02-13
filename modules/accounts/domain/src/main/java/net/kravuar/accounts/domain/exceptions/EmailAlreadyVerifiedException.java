package net.kravuar.accounts.domain.exceptions;

public class EmailAlreadyVerifiedException extends RuntimeException {
    public EmailAlreadyVerifiedException() {
        super("Email already verified");
    }
}
