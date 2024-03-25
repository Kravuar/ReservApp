package net.kravuar.schedule.domain.exceptions;

public class ServiceNotFoundException extends ScheduleException {
    public ServiceNotFoundException() {
        super("Service not found");
    }
}
