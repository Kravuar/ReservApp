package net.kravuar.schedule.domain.exceptions;

public class ReservationOverlappingException extends ScheduleException {
    public ReservationOverlappingException() {
        super("Reservation overlaps with staff's another reservation");
    }
}
