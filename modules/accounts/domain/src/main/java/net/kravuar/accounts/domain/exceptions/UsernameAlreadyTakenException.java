package net.kravuar.accounts.domain.exceptions;

public class UsernameAlreadyTakenException extends AccountException {
    public UsernameAlreadyTakenException() {
        super("Username already taken");
    }
}
