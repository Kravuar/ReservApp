package net.kravuar.staff.domain.exceptions;

public class StaffNotFoundException extends RuntimeException {
    public StaffNotFoundException() {
        super("Staff not found");
    }
}
