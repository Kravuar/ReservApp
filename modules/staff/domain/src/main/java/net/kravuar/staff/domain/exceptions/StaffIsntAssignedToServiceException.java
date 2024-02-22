package net.kravuar.staff.domain.exceptions;

public class StaffIsntAssignedToServiceException extends RuntimeException {
    public StaffIsntAssignedToServiceException() {
        super("Staff isn't assigned to service");
    }
}
