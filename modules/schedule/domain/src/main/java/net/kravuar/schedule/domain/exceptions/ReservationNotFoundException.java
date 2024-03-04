package net.kravuar.schedule.domain.exceptions;

public class ReservationNotFoundException extends ScheduleException {
    public ReservationNotFoundException() {
        super("Reservation not found");
    }
}
