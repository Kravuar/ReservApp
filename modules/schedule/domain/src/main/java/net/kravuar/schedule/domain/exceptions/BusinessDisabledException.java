package net.kravuar.schedule.domain.exceptions;

public class BusinessDisabledException extends ScheduleException {
    public BusinessDisabledException() {
        super("Business is disabled");
    }
}
