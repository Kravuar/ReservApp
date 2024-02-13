package net.kravuar.accounts.domain.exceptions;

public class EmailAlreadyTakenException extends RuntimeException {
    public EmailAlreadyTakenException() {
        super("Email already taken");
    }
}
