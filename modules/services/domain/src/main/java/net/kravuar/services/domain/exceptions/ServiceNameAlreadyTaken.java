package net.kravuar.services.domain.exceptions;

public class ServiceNameAlreadyTaken extends ServiceException {
    public ServiceNameAlreadyTaken() {
        super("Service name already taken");
    }
}
