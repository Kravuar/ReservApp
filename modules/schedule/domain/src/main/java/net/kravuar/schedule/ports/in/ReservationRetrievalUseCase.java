package net.kravuar.schedule.ports.in;

import net.kravuar.schedule.domain.Reservation;
import net.kravuar.schedule.domain.exceptions.ReservationNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRetrievalUseCase {
    /**
     * Find reservation by id.
     *
     * @param reservationId id of the reservation
     * @return {@link Reservation} associated with the provided {@code reservationId}
     * @throws ReservationNotFoundException if reservation wasn't found
     */
    Reservation findById(long reservationId);

    /**
     * Find all active reservations by staff within a date range.
     *
     * @param staffId id of the staff
     * @param from     start date of the range
     * @param to       end date of the range (inclusive)
     * @return {@code List<Reservation>} of reservations associated with the provided {@code staffId}
     * within the specified date range
     */
    List<Reservation> findAllByStaff(long staffId, LocalDate from, LocalDate to);

    /**
     * Find all active reservations by client within a date range.
     *
     * @param clientSub unique identifier of the client
     * @param from      start date of the range
     * @param to        end date of the range (inclusive)
     * @return {@code List<Reservation>} of reservations associated with the provided {@code clientSub}
     * within the specified date range
     */
    List<Reservation> findAllByClient(String clientSub, LocalDate from, LocalDate to);
}
