package net.kravuar.schedule.domain.exceptions;

public class ReservationOutOfSlotsException extends ScheduleException {
    public ReservationOutOfSlotsException() {
        super("No available slots left for reservation");
    }
}
