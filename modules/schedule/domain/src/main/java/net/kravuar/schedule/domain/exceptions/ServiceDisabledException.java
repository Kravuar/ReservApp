package net.kravuar.schedule.domain.exceptions;

public class ServiceDisabledException extends ScheduleException {
    public ServiceDisabledException() {
        super("Service is disabled");
    }
}
