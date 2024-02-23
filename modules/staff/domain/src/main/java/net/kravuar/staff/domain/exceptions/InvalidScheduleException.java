package net.kravuar.staff.domain.exceptions;

public class InvalidScheduleException extends StaffException {
    public InvalidScheduleException() {
        super("Invalid schedule");
    }
}
