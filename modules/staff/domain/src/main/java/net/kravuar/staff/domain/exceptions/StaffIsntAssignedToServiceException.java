package net.kravuar.staff.domain.exceptions;

public class StaffIsntAssignedToServiceException extends StaffException {
    public StaffIsntAssignedToServiceException() {
        super("Staff isn't assigned to service");
    }
}
