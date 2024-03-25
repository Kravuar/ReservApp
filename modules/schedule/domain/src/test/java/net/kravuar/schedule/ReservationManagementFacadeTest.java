package net.kravuar.schedule;

import net.kravuar.schedule.domain.Business;
import net.kravuar.schedule.domain.Reservation;
import net.kravuar.schedule.domain.Service;
import net.kravuar.schedule.domain.Staff;
import net.kravuar.schedule.domain.commands.CreateReservationCommand;
import net.kravuar.schedule.domain.commands.RetrieveScheduleByStaffAndServiceCommand;
import net.kravuar.schedule.domain.exceptions.ReservationOutOfSlotsException;
import net.kravuar.schedule.domain.exceptions.ReservationOverlappingException;
import net.kravuar.schedule.domain.exceptions.ReservationSlotNotFoundException;
import net.kravuar.schedule.domain.weak.ReservationSlot;
import net.kravuar.schedule.ports.in.ScheduleRetrievalUseCase;
import net.kravuar.schedule.ports.out.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ReservationManagementFacadeTest {
    @Mock
    ReservationPersistencePort reservationPersistencePort;
    @Mock
    ReservationRetrievalPort reservationRetrievalPort;
    @Mock
    StaffRetrievalPort staffRetrievalPort;
    @Mock
    ServiceRetrievalPort serviceRetrievalPort;
    @Mock
    ScheduleLockPort scheduleLockPort;
    @Mock
    ScheduleRetrievalUseCase scheduleRetrievalUseCase;
    @InjectMocks
    ReservationManagementFacade reservationManagement;

    static final LocalDateTime RESERVATION_DATE_TIME = LocalDateTime.of(2024, 1, 1, 12, 0, 0);

    static Business someBusiness() {
        return new Business(1L, "owner", true);
    }

    static Service someService() {
        return new Service(1L, someBusiness(), true);
    }

    static Service someOtherService() {
        return new Service(2L, someBusiness(), true);
    }

    static Staff someStaff() {
        return new Staff(1L, someBusiness(), "sub", true);
    }

    @Test
    void givenValidCreateReservationCommand_AndNoExistingReservations_whenCreateReservation_thenReservationCreated() {
        // Given
        CreateReservationCommand command = new CreateReservationCommand(
                "sub",
                1,
                1,
                RESERVATION_DATE_TIME
        );
        Staff staff = someStaff();
        Service service = someService();
        LocalDate date = command.dateTime().toLocalDate();

        ReservationSlot reservationSlot = new ReservationSlot(
                command.dateTime().toLocalTime(),
                command.dateTime().toLocalTime().plusHours(1),
                5,
                1
        );
        var existingReservations = new TreeMap<>(Collections.singletonMap(
                date,
                Collections.emptyList()
        ));
        var existingSlots = new TreeMap<>(Collections.singletonMap(
                date,
                new TreeSet<>(Set.of(reservationSlot))
        ));

        doReturn(staff)
                .when(staffRetrievalPort)
                .findActiveById(command.staffId());
        doReturn(service)
                .when(serviceRetrievalPort)
                .findActiveById(command.serviceId());
        doReturn(existingSlots)
                .when(scheduleRetrievalUseCase)
                .findActiveScheduleByStaffAndServiceInPerDay(any());
        doReturn(existingReservations)
                .when(reservationRetrievalPort)
                .findAllActiveByStaff(anyLong(), eq(date), eq(date), eq(false));
        doAnswer(invocation -> invocation.getArgument(0))
                .when(reservationPersistencePort)
                .save(any());

        // When
        Reservation reservation = reservationManagement.createReservation(command);

        // Then
        assertThat(reservation).isNotNull();
        assertThat(reservation.getDate()).isEqualTo(date);
        assertThat(reservation.getStart()).isEqualTo(reservationSlot.getStart());
        assertThat(reservation.getEnd()).isEqualTo(reservationSlot.getEnd());
        assertThat(reservation.getClientSub()).isEqualTo(command.sub());
        assertThat(reservation.getStaff()).isSameAs(staff);
        assertThat(reservation.getService()).isSameAs(service);
        verify(reservationPersistencePort).save(any(Reservation.class));
        verify(scheduleLockPort).lockByStaff(eq(command.staffId()), eq(false));
    }

    @Test
    void givenValidCreateReservationCommand_AndNoReservationSlotForDateTime_whenCreateReservation_thenReservationSlotNotFoundExceptionThrown() {
        // Given
        CreateReservationCommand command = new CreateReservationCommand(
                "sub",
                1,
                1,
                RESERVATION_DATE_TIME
        );

        doReturn(someStaff())
                .when(staffRetrievalPort)
                .findActiveById(eq(command.staffId()));
        doReturn(someService())
                .when(serviceRetrievalPort)
                .findActiveById(eq(command.serviceId()));
        // No reservation slots are available for the given dateTime
        doReturn(Collections.emptyNavigableMap())
                .when(scheduleRetrievalUseCase)
                .findActiveScheduleByStaffAndServiceInPerDay(any(RetrieveScheduleByStaffAndServiceCommand.class));

        // When & Then
        assertThatThrownBy(() -> reservationManagement.createReservation(command))
                .isInstanceOf(ReservationSlotNotFoundException.class);

        verify(scheduleLockPort).lockByStaff(eq(command.staffId()), eq(false));
        verifyNoMoreInteractions(reservationPersistencePort);
    }

    @Test
    void givenValidCreateReservationCommand_AndOverlapWithExistingReservationFromSameService_ButHasAvailableSlots_whenCreateReservation_thenReservationCreated() {
        // Given
        CreateReservationCommand command = new CreateReservationCommand(
                "sub",
                1,
                1,
                RESERVATION_DATE_TIME
        );
        Staff staff = someStaff();
        Service service = someService();
        LocalDate date = command.dateTime().toLocalDate();

        ReservationSlot reservationSlot = new ReservationSlot(
                command.dateTime().toLocalTime(),
                command.dateTime().toLocalTime().plusHours(1),
                100,
                3
        );

        // 2 existing reservations for same slot
        var existingReservations = new TreeMap<>(Collections.singletonMap(
                date,
                List.of(
                        new Reservation(null, date, reservationSlot.getStart(), reservationSlot.getEnd(), "some sub", staff, service, true),
                        new Reservation(null, date, reservationSlot.getStart(), reservationSlot.getEnd(), "some sub", staff, service, true)
                )
        ));
        var existingSlots = new TreeMap<>(Collections.singletonMap(
                date,
                new TreeSet<>(Set.of(reservationSlot))
        ));

        doReturn(staff)
                .when(staffRetrievalPort)
                .findActiveById(eq(command.staffId()));
        doReturn(service)
                .when(serviceRetrievalPort)
                .findActiveById(eq(command.serviceId()));
        doReturn(existingSlots)
                .when(scheduleRetrievalUseCase)
                .findActiveScheduleByStaffAndServiceInPerDay(any(RetrieveScheduleByStaffAndServiceCommand.class));
        doReturn(existingReservations)
                .when(reservationRetrievalPort)
                .findAllActiveByStaff(anyLong(), eq(date), eq(date), eq(false));
        doAnswer(invocation -> invocation.getArgument(0))
                .when(reservationPersistencePort)
                .save(any());

        // When
        Reservation reservation = reservationManagement.createReservation(command);

        // Then
        assertThat(reservation).isNotNull();
        assertThat(reservation.getDate()).isEqualTo(date);
        assertThat(reservation.getStart()).isEqualTo(reservationSlot.getStart());
        assertThat(reservation.getEnd()).isEqualTo(reservationSlot.getEnd());
        assertThat(reservation.getClientSub()).isEqualTo(command.sub());
        assertThat(reservation.getStaff()).isSameAs(staff);
        assertThat(reservation.getService()).isSameAs(service);
        verify(reservationPersistencePort).save(any(Reservation.class));
        verify(scheduleLockPort).lockByStaff(eq(command.staffId()), eq(false));
    }

    @Test
    void givenValidCreateReservationCommand_AndOverlapWithExistingReservationFromSameService_ButHasNoAvailableSlots_whenCreateReservation_thenThrowsReservationOutOfSlotsException() {
        // Given
        CreateReservationCommand command = new CreateReservationCommand(
                "sub",
                1,
                1,
                RESERVATION_DATE_TIME
        );
        Staff staff = someStaff();
        Service service = someService();
        LocalDate date = command.dateTime().toLocalDate();

        ReservationSlot reservationSlot = new ReservationSlot(
                command.dateTime().toLocalTime(),
                command.dateTime().toLocalTime().plusHours(1),
                100,
                2
        );

        // 2 existing reservations for same slot
        var existingReservations = new TreeMap<>(Collections.singletonMap(
                date,
                List.of(
                        new Reservation(null, date, reservationSlot.getStart(), reservationSlot.getEnd(), "some sub", staff, service, true),
                        new Reservation(null, date, reservationSlot.getStart(), reservationSlot.getEnd(), "some sub", staff, service, true)
                )
        ));
        var existingSlots = new TreeMap<>(Collections.singletonMap(
                date,
                new TreeSet<>(Set.of(reservationSlot))
        ));

        doReturn(staff)
                .when(staffRetrievalPort)
                .findActiveById(eq(command.staffId()));
        doReturn(service)
                .when(serviceRetrievalPort)
                .findActiveById(eq(command.serviceId()));
        doReturn(existingSlots)
                .when(scheduleRetrievalUseCase)
                .findActiveScheduleByStaffAndServiceInPerDay(any(RetrieveScheduleByStaffAndServiceCommand.class));
        doReturn(existingReservations)
                .when(reservationRetrievalPort)
                .findAllActiveByStaff(anyLong(), eq(date), eq(date), eq(false));

        // When & Then
        assertThatThrownBy(() -> reservationManagement.createReservation(command))
                .isInstanceOf(ReservationOutOfSlotsException.class);

        verifyNoMoreInteractions(reservationPersistencePort);
        verify(scheduleLockPort).lockByStaff(eq(command.staffId()), eq(false));
    }

    @Test
    void givenValidCreateReservationCommand_AndOverlapWithExistingReservationFromOtherService_whenCreateReservation_thenReservationOverlappingExceptionThrown() {
        // Given
        CreateReservationCommand command = new CreateReservationCommand(
                "sub",
                1,
                1,
                RESERVATION_DATE_TIME
        );
        Staff staff = someStaff();
        Service service = someService();
        Service otherService = someOtherService();
        LocalDate date = command.dateTime().toLocalDate();

        ReservationSlot reservationSlot = new ReservationSlot(
                command.dateTime().toLocalTime(),
                command.dateTime().toLocalTime().plusHours(1),
                1,
                1
        );

        // Existing reservation from other services that overlap with the new reservation
        var existingReservations = new TreeMap<>(Collections.singletonMap(
                date,
                List.of(
                        new Reservation(null, date, reservationSlot.getStart().minusMinutes(30), reservationSlot.getEnd().plusMinutes(30), "existing sub", staff, otherService, true)
                )
        ));
        var existingSlots = new TreeMap<>(Collections.singletonMap(
                date,
                new TreeSet<>(Set.of(reservationSlot))
        ));

        doReturn(staff)
                .when(staffRetrievalPort)
                .findActiveById(eq(command.staffId()));
        doReturn(service)
                .when(serviceRetrievalPort)
                .findActiveById(eq(command.serviceId()));
        doReturn(existingSlots)
                .when(scheduleRetrievalUseCase)
                .findActiveScheduleByStaffAndServiceInPerDay(any(RetrieveScheduleByStaffAndServiceCommand.class));
        doReturn(existingReservations)
                .when(reservationRetrievalPort)
                .findAllActiveByStaff(anyLong(), eq(date), eq(date), eq(false));

        // When & Then
        assertThatThrownBy(() -> reservationManagement.createReservation(command))
                .isInstanceOf(ReservationOverlappingException.class);

        verifyNoMoreInteractions(reservationPersistencePort);
        verify(scheduleLockPort).lockByStaff(eq(command.staffId()), eq(false));
    }

    @Test
    void givenValidReservationId_whenCancelReservation_thenReservationCanceled() {
        // Given
        Staff staff = someStaff();
        Service service = someService();
        long reservationId = 1;
        Reservation reservation = spy(new Reservation(
                reservationId,
                RESERVATION_DATE_TIME.toLocalDate(),
                RESERVATION_DATE_TIME.toLocalTime(),
                RESERVATION_DATE_TIME.toLocalTime(),
                "sub",
                staff,
                service,
                true
        ));

        doReturn(reservation)
                .when(reservationRetrievalPort)
                .findActiveById(eq(reservationId));

        // When
        reservationManagement.cancelReservation(reservationId);

        // Then
        verify(reservation).setActive(false);
        verify(reservationPersistencePort).save(same(reservation));
        verify(scheduleLockPort).lockByStaff(eq(reservation.getStaff().getId()), eq(false));
        verify(scheduleLockPort).lock(eq(reservationId), eq(false));
    }
}