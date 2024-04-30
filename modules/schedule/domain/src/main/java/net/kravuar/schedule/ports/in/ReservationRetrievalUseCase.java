package net.kravuar.schedule.ports.in;

import net.kravuar.schedule.domain.exceptions.ReservationNotFoundException;
import net.kravuar.staff.model.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.SortedMap;

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
     * @param from    start date of the range
     * @param to      end date of the range (inclusive)
     * @return {@code SortedMap<LocalDate, List<Reservation>>} of reservations associated with the provided {@code staffId}
     * within the specified date range
     */
    SortedMap<LocalDate, List<Reservation>> findAllByStaff(long staffId, LocalDate from, LocalDate to);

    /**
     * Find all active reservations by staff subject within a date range.
     *
     * @param sub subject of the staff
     * @param from    start date of the range
     * @param to      end date of the range (inclusive)
     * @return {@code SortedMap<LocalDate, List<Reservation>>} of reservations associated with the provided {@code staffId}
     * within the specified date range
     */
    SortedMap<LocalDate, List<Reservation>> findAllByStaff(String sub, LocalDate from, LocalDate to);

    /**
     * Find all active reservations by client within a date range.
     *
     * @param clientSub unique identifier of the client
     * @param from      start date of the range
     * @param to        end date of the range (inclusive)
     * @return {@code SortedMap<LocalDate, List<Reservation>>} of reservations associated with the provided {@code clientSub}
     * within the specified date range
     */
    SortedMap<LocalDate, List<Reservation>> findAllByClient(String clientSub, LocalDate from, LocalDate to);

    /**
     * Find all active reservations by service within a date range.
     *
     * @param serviceId id of the service
     * @param from      start date of the range
     * @param to        end date of the range (inclusive)
     * @return {@code SortedMap<LocalDate, List<Reservation>>} of reservations associated with the provided {@code serviceId}
     * within the specified date range
     */
    SortedMap<LocalDate, List<Reservation>> findAllByService(long serviceId, LocalDate from, LocalDate to);
}
