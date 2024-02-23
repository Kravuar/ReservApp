package net.kravuar.staff.domain.exceptions;

public class ServiceNotFoundException extends StaffException {
    public ServiceNotFoundException() {
        super("Service not found");
    }
}
