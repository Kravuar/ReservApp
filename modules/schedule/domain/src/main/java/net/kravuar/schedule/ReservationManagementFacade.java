package net.kravuar.schedule;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.staff.model.Reservation;
import net.kravuar.staff.model.Service;
import net.kravuar.staff.model.Staff;
import net.kravuar.schedule.domain.commands.CreateReservationCommand;
import net.kravuar.schedule.domain.commands.RetrieveScheduleByStaffAndServiceCommand;
import net.kravuar.schedule.domain.exceptions.ReservationOutOfSlotsException;
import net.kravuar.schedule.domain.exceptions.ReservationOverlappingException;
import net.kravuar.schedule.domain.exceptions.ReservationSlotNotFoundException;
import net.kravuar.staff.model.weak.ReservationSlot;
import net.kravuar.schedule.ports.in.ReservationManagementUseCase;
import net.kravuar.schedule.ports.in.ScheduleRetrievalUseCase;
import net.kravuar.schedule.ports.out.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AppComponent
@RequiredArgsConstructor
public class ReservationManagementFacade implements ReservationManagementUseCase {
    private final ReservationPersistencePort reservationPersistencePort;
    private final ReservationRetrievalPort reservationRetrievalPort;
    private final StaffRetrievalPort staffRetrievalPort;
    private final ServiceRetrievalPort serviceRetrievalPort;
    private final ScheduleLockPort scheduleLockPort;
    // TODO: Surely i shouldn't use a use case in another use case, need another driven port here
    private final ScheduleRetrievalUseCase scheduleRetrievalUseCase;

    @Override
    public Reservation createReservation(CreateReservationCommand command) {
        try {
            scheduleLockPort.lockByStaff(command.staffId(), true);

            Staff staff = staffRetrievalPort.findActiveById(command.staffId());
            Service service = serviceRetrievalPort.findActiveById(command.serviceId());

            LocalDate date = command.dateTime().toLocalDate();
            LocalTime time = command.dateTime().toLocalTime();

            ReservationSlot reservationSlot = getSlot(command.staffId(), command.serviceId(), date, time);

            validateReservationSlot(reservationSlot, command.staffId(), command.serviceId(), command.dateTime().toLocalDate());

            Reservation reservation = new Reservation(
                    null,
                    date,
                    reservationSlot.getStart(),
                    reservationSlot.getEnd(),
                    command.sub(),
                    staff,
                    service,
                    true
            );

            return reservationPersistencePort.save(reservation);
        } finally {
            scheduleLockPort.lockByStaff(command.staffId(), false);
        }
    }

    @Override
    public Reservation cancelReservation(long reservationId) {
        try {
            scheduleLockPort.lock(reservationId, true);

            Reservation reservation = reservationRetrievalPort.findActiveById(reservationId);
            try {
                scheduleLockPort.lockByStaff(reservation.getStaff().getId(), true);

                reservation.setActive(false);
                reservationPersistencePort.save(reservation);
                return reservation;
            } finally {
                scheduleLockPort.lockByStaff(reservation.getStaff().getId(), false);
            }
        } finally {
            scheduleLockPort.lock(reservationId, false);
        }
    }

    @Override
    public Reservation restoreReservation(long reservationId) {
        try {
            scheduleLockPort.lock(reservationId, true);
            Reservation reservation = reservationRetrievalPort.findActiveById(reservationId);

            if (reservation.isActive())
                throw new IllegalStateException("Reservation isn't cancelled");

            try {
                scheduleLockPort.lockByStaff(reservation.getStaff().getId(), true);

                ReservationSlot slot = getSlot(reservation.getStaff().getId(), reservation.getService().getId(), reservation.getDate(), reservation.getStart());

                validateReservationSlot(slot, reservation.getStaff().getId(), reservation.getService().getId(), reservation.getDate());

                reservation.setActive(true);
                reservationPersistencePort.save(reservation);
                return reservation;
            } finally {
                scheduleLockPort.lockByStaff(reservation.getStaff().getId(), false);
            }
        } finally {
            scheduleLockPort.lock(reservationId, false);
        }
    }

    private void validateReservationSlot(ReservationSlot reservationSlot, long staffId, long serviceId, LocalDate date) {
        // Non fully active as well, so that, if some parent entity went inactive before we fetch reservations
        // we will still see them, which will prevent placing multiple reservations at the same time (due to
        // existing is not visible at the moment).
        List<Reservation> existingReservations = reservationRetrievalPort.findAllActiveByStaff(
                staffId,
                date,
                date,
                false
        ).getOrDefault(date, Collections.emptyList());
        Map<Boolean, List<Reservation>> partitionedBySameService = existingReservations.stream()
                .collect(Collectors.partitioningBy(
                        existing -> existing.getService().getId().equals(serviceId)
                ));

        // Should not exceed reservation slot size if overlaps in same service
        List<Reservation> sameServiceAndSlotReservations = partitionedBySameService.get(true).stream()
                .filter(sameServiceReservation -> sameServiceReservation.getStart().equals(reservationSlot.getStart()))
                .toList();
        int takenSlots = sameServiceAndSlotReservations.size();
        if (takenSlots >= reservationSlot.getMaxReservations()) // ?Maybe >= check is safer than == somehow?
            throw new ReservationOutOfSlotsException();

        // Should not overlap with ANY from other service
        // Given [x1:x2] [y1:y2], overlaps when: x1 <= y2 && y1 <= x2
        List<Reservation> otherServicesReservations = partitionedBySameService.get(false);
        for (Reservation otherServiceReservation: otherServicesReservations)
            if (reservationSlot.getStart().isBefore(otherServiceReservation.getEnd()) && otherServiceReservation.getStart().isBefore(reservationSlot.getEnd()))
                throw new ReservationOverlappingException();
    }

    private ReservationSlot getSlot(long staffId, long serviceId, LocalDate date, LocalTime time) {
        // Find all slots by day, pick one, that matches reservation time
        return scheduleRetrievalUseCase.findActiveScheduleByStaffAndServiceInPerDay(
                        new RetrieveScheduleByStaffAndServiceCommand(
                                staffId,
                                serviceId,
                                date,
                                date
                        )
                ).getOrDefault(date, Collections.emptySortedSet()).stream()
                .filter(slot -> slot.getStart().equals(time))
                .findAny()
                .orElseThrow(ReservationSlotNotFoundException::new);
    }
}
