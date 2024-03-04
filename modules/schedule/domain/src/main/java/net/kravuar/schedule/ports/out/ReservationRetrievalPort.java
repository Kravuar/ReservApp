package net.kravuar.schedule.ports.out;

import net.kravuar.schedule.domain.Reservation;
import net.kravuar.schedule.domain.exceptions.ReservationNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRetrievalPort {
    /**
     * Find active reservation by id.
     * Only with active parent entities (business, service and staff),
     * otherwise schedule exception day should not be visible.
     *
     * @param reservationId id of the reservation
     * @return {@link Reservation} associated with the provided {@code reservationId}
     * @throws ReservationNotFoundException if reservation wasn't found
     */
    Reservation findActiveById(long reservationId);

    /**
     * Find all active reservations by staff within a date range.
     *
     * @param staffId id of the staff
     * @param from     start date of the range
     * @param to       end date of the range (inclusive)
     * @param fullyActiveOnly whether to search fully active reservations only (with active parent entities)
     * @return {@code List<Reservation>} of reservations associated with the provided {@code staffId}
     * within the specified date range
     */
    List<Reservation> findAllByStaff(long staffId, LocalDate from, LocalDate to, boolean fullyActiveOnly);

    /**
     * Find all active reservations by client within a date range.
     * Only with active parent entities (business, service and staff),
     * otherwise schedule exception day should not be visible.
     *
     * @param clientSub unique identifier of the client
     * @param from      start date of the range
     * @param to        end date of the range (inclusive)
     * @return {@code List<Reservation>} of reservations associated with the provided {@code clientSub}
     * within the specified date range
     */
    List<Reservation> findAllActiveByClient(String clientSub, LocalDate from, LocalDate to);
}