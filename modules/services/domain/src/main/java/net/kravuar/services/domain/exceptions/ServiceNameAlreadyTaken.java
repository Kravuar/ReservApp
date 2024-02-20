package net.kravuar.services.domain.exceptions;

public class ServiceNameAlreadyTaken extends RuntimeException {
    public ServiceNameAlreadyTaken() {
        super("Service name already taken");
    }
}
