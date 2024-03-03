package net.kravuar.schedule.domain.exceptions;

public class ScheduleExceptionNotFoundException extends ScheduleException {
    public ScheduleExceptionNotFoundException() {
        super("Schedule exception day not found");
    }
}
