package net.kravuar.services.domain.exceptions;

public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}
