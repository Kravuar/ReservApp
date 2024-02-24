package net.kravuar.services.domain.exceptions;

public class BusinessNotFoundException extends ServiceException {
    public BusinessNotFoundException() {
        super("Business not found");
    }
}
