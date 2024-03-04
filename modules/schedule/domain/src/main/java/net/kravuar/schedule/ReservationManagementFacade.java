package net.kravuar.schedule;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.schedule.domain.Reservation;
import net.kravuar.schedule.domain.Service;
import net.kravuar.schedule.domain.Staff;
import net.kravuar.schedule.domain.commands.CreateReservationCommand;
import net.kravuar.schedule.domain.commands.RetrieveScheduleByStaffAndServiceCommand;
import net.kravuar.schedule.domain.weak.WorkingHours;
import net.kravuar.schedule.ports.in.ReservationManagementUseCase;
import net.kravuar.schedule.ports.in.ScheduleRetrievalUseCase;
import net.kravuar.schedule.ports.out.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

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
            List<WorkingHours> workingHours = scheduleRetrievalUseCase.findActiveScheduleByStaffAndServiceInPerDay(
                    new RetrieveScheduleByStaffAndServiceCommand(
                            command.staffId(),
                            command.serviceId(),
                            date,
                            date
                    )
            ).getOrDefault(date, Collections.emptyList());

            WorkingHours reservationHours = workingHours.stream()
                    .filter(hours -> time.isAfter(hours.getStart()) && time.isBefore(hours.getEnd()))
                    .findAny()
                    .orElseThrow(() -> new IllegalStateException("No working hours available for reservation"));
            // TODO: Check if service duration fits in with this reservation (reservation.time + duration < hours.end)

            // Non fully active as well, so that, if some parent entity went inactive before we fetch reservations
            // we will still see them, which will prevent placing multiple reservations at the same time (due to
            // existing is not visible at the moment)
            List<Reservation> existingReservations = reservationRetrievalPort.findAllByStaff(
                    command.staffId(),
                    command.dateTime().toLocalDate(),
                    command.dateTime().toLocalDate(),
                    false
            );
            // TODO: if single slot - should not overlap with any other reservation
            // TODO: if multi-slot - should not exceed, and should start at the same time as existing (or be the one who dictates the start, if no other present)

            Reservation reservation = new Reservation(
                    null,
                    command.dateTime(),
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
    public void cancelReservation(long reservationId) {

    }
}
