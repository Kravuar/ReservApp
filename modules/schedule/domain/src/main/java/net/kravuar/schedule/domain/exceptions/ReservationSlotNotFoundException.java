package net.kravuar.schedule.domain.exceptions;

public class ReservationSlotNotFoundException extends ScheduleException {
    public ReservationSlotNotFoundException() {
        super("Reservation slot not found");
    }
}
