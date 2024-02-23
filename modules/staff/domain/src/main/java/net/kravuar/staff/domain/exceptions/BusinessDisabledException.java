package net.kravuar.staff.domain.exceptions;

public class BusinessDisabledException extends StaffException {
    public BusinessDisabledException() {
        super("Business is disabled");
    }
}
