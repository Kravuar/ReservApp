package net.kravuar.business.domain.exceptions;

public class AccountNotFoundException extends BusinessException {
    public AccountNotFoundException() {
        super("Account not found");
    }
}
