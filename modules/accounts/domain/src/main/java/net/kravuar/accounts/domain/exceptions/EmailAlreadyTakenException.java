package net.kravuar.accounts.domain.exceptions;

public class EmailAlreadyTakenException extends AccountException {
    public EmailAlreadyTakenException() {
        super("Email already taken");
    }
}
