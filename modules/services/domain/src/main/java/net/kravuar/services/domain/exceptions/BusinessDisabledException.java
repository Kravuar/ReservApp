package net.kravuar.services.domain.exceptions;

public class BusinessDisabledException extends RuntimeException {
    public BusinessDisabledException() {
        super("Business is disabled");
    }
}
