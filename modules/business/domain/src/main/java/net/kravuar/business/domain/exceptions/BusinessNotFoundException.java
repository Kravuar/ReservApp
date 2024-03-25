package net.kravuar.business.domain.exceptions;

public class BusinessNotFoundException extends BusinessException {
    public BusinessNotFoundException() {
        super("Business not found");
    }
}
