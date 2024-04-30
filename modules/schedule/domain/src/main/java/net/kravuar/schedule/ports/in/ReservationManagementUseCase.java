package net.kravuar.schedule.ports.in;

import jakarta.validation.Valid;
import net.kravuar.context.AppValidated;
import net.kravuar.staff.model.Reservation;
import net.kravuar.schedule.domain.commands.CreateReservationCommand;
import net.kravuar.schedule.domain.exceptions.ReservationNotFoundException;
import net.kravuar.schedule.domain.exceptions.StaffNotFoundException;

@AppValidated
public interface ReservationManagementUseCase {
    /**
     * Create a reservation.
     *
     * @param command command containing details of the reservation creation
     * @return {@link Reservation} created reservation
     * @throws StaffNotFoundException if staff wasn't found
     * @throws IllegalStateException  if reservation overlaps with other reservations
     *                                of the staff or if there are no available reservation slots
     */
    Reservation createReservation(@Valid CreateReservationCommand command);

    /**
     * Cancel a reservation.
     *
     * @param reservationId id of the reservation
     * @return {@link Reservation} canceled reservation
     * @throws ReservationNotFoundException if reservation wasn't found
     */
    Reservation cancelReservation(long reservationId);

    /**
     * Restore a reservation.
     *
     * @param reservationId id of the reservation
     * @return {@link Reservation} restored reservation
     * @throws StaffNotFoundException if staff wasn't found
     * @throws IllegalStateException  if reservation is active, overlaps with other reservations
     *                                of the staff or if there are no available reservation slots
     */
    Reservation restoreReservation(long reservationId);
}