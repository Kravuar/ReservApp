package net.kravuar.services.domain.exceptions;

public class ServiceNotFoundException extends ServiceException {
    public ServiceNotFoundException() {
        super("Service not found");
    }
}
