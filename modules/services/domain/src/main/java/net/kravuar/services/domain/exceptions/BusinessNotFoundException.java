package net.kravuar.services.domain.exceptions;

public class BusinessNotFoundException extends RuntimeException {
    public BusinessNotFoundException() {
        super("Business not found");
    }
}
