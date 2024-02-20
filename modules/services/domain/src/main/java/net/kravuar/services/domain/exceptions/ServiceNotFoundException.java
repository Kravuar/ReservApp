package net.kravuar.services.domain.exceptions;

public class ServiceNotFoundException extends RuntimeException {
    public ServiceNotFoundException() {
        super("Service not found");
    }
}
