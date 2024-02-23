package net.kravuar.staff.domain.exceptions;

public class BusinessNotFoundException extends StaffException {
    public BusinessNotFoundException() {
        super("Business not found");
    }
}
