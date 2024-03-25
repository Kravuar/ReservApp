package net.kravuar.staff.domain.exceptions;

public class AccountNotFoundException extends StaffException {
    public AccountNotFoundException() {
        super("Account not found");
    }
}
