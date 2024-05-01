package net.kravuar.schedule.ports.out;

import net.kravuar.schedule.model.Reservation;

public interface ReservationPersistencePort {
    /**
     * Save reservation.
     *
     * @param reservation new reservation object to save
     */
    Reservation save(Reservation reservation);
}
