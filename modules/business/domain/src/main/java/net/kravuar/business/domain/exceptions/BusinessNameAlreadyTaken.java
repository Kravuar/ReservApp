package net.kravuar.business.domain.exceptions;

public class BusinessNameAlreadyTaken extends RuntimeException {
    public BusinessNameAlreadyTaken() {
        super("Business name already taken");
    }
}
