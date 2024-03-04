package net.kravuar.schedule;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.schedule.domain.Reservation;
import net.kravuar.schedule.domain.ReservationSlot;
import net.kravuar.schedule.domain.Service;
import net.kravuar.schedule.domain.Staff;
import net.kravuar.schedule.domain.commands.CreateReservationCommand;
import net.kravuar.schedule.domain.commands.RetrieveScheduleByStaffAndServiceCommand;
import net.kravuar.schedule.domain.exceptions.ReservationOutOfSlotsException;
import net.kravuar.schedule.domain.exceptions.ReservationOverlappingException;
import net.kravuar.schedule.domain.exceptions.ReservationSlotNotFoundException;
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

            // Find all slots by day, pick one, that matches reservation time
            ReservationSlot reservationSlot = scheduleRetrievalUseCase.findActiveScheduleByStaffAndServiceInPerDay(
                            new RetrieveScheduleByStaffAndServiceCommand(
                                    command.staffId(),
                                    command.serviceId(),
                                    date,
                                    date
                            )
                    ).getOrDefault(date, Collections.emptySortedSet()).stream()
                    .filter(slot -> slot.getStart().equals(time))
                    .findAny()
                    .orElseThrow(ReservationSlotNotFoundException::new);

            // Non fully active as well, so that, if some parent entity went inactive before we fetch reservations
            // we will still see them, which will prevent placing multiple reservations at the same time (due to
            // existing is not visible at the moment).
            List<Reservation> existingReservations = reservationRetrievalPort.findAllByStaff(
                    command.staffId(),
                    command.dateTime().toLocalDate(),
                    command.dateTime().toLocalDate(),
                    false
            ).getOrDefault(date, Collections.emptyList());

            Reservation reservation = new Reservation(
                    null,
                    reservationSlot,
                    date,
                    command.sub(),
                    staff,
                    service,
                    true
            );

            Map<Boolean, List<Reservation>> partitionedBySameService = existingReservations.stream()
                    .collect(Collectors.partitioningBy(
                            existing -> existing.getService().getId().equals(service.getId())
                    ));

            // Should not exceed reservation slot size if overlaps
            List<Reservation> sameServiceAndSlotReservations = partitionedBySameService.get(true).stream()
                    .filter(sameServiceReservation -> sameServiceReservation.getSlot().getStart().equals(reservationSlot.getStart()))
                    .toList();
            int takenSlots = sameServiceAndSlotReservations.size();
            if (reservationSlot.getMaxReservations() == takenSlots) // ?Maybe >= check is safer somehow?
                throw new ReservationOutOfSlotsException();

            // Should not overlap with ANY from other service
            List<Reservation> otherServicesReservations = partitionedBySameService.get(true);
            for (Reservation otherServiceReservation: otherServicesReservations) {
                ReservationSlot otherSlot = otherServiceReservation.getSlot();
                if (otherSlot.getStart().isBefore(reservationSlot.getStart())
                        && otherSlot.getEnd().isAfter(reservationSlot.getStart()))
                    throw new ReservationOverlappingException();
            }

            return reservationPersistencePort.save(reservation);
        } finally {
            scheduleLockPort.lockByStaff(command.staffId(), false);
        }
    }

    @Override
    public void cancelReservation(long reservationId) {

    }
}
