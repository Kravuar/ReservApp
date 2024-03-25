package net.kravuar.staff.domain.exceptions;

public class StaffNotFoundException extends StaffException {
    public StaffNotFoundException() {
        super("Staff not found");
    }
}
