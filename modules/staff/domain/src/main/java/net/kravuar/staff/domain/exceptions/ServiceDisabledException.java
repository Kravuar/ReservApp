package net.kravuar.staff.domain.exceptions;

public class ServiceDisabledException extends StaffException {
    public ServiceDisabledException() {
        super("Service is disabled");
    }
}
