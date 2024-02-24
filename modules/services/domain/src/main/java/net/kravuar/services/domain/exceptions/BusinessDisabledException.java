package net.kravuar.services.domain.exceptions;

public class BusinessDisabledException extends ServiceException {
    public BusinessDisabledException() {
        super("Business is disabled");
    }
}
