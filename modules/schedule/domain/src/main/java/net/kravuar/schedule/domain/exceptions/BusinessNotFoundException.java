package net.kravuar.schedule.domain.exceptions;

public class BusinessNotFoundException extends ScheduleException {
    public BusinessNotFoundException() {
        super("Business not found");
    }
}
