package net.kravuar.business.domain.exceptions;

public class BusinessNameAlreadyTaken extends BusinessException {
    public BusinessNameAlreadyTaken() {
        super("Business name already taken");
    }
}
