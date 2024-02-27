package net.kravuar.schedule.domain.exceptions;

public class ScheduleNotFoundException extends ScheduleException {
    public ScheduleNotFoundException() {
        super("Schedule not found");
    }
}
