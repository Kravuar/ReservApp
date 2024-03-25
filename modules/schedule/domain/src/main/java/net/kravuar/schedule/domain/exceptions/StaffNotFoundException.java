package net.kravuar.schedule.domain.exceptions;

public class StaffNotFoundException extends ScheduleException {
    public StaffNotFoundException() {
        super("Staff not found");
    }
}
