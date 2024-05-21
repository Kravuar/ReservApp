package net.kravuar.schedule;

import net.kravuar.schedule.domain.commands.*;
import net.kravuar.staff.model.*;
import net.kravuar.staff.model.weak.ReservationSlot;
import net.kravuar.schedule.ports.out.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ScheduleManagementFacadeTest {
    @Mock
    ScheduleRetrievalPort scheduleRetrievalPort;
    @Mock
    ReservationRetrievalPort reservationRetrievalPort;
    @Mock
    StaffRetrievalPort staffRetrievalPort;
    @Mock
    ServiceRetrievalPort serviceRetrievalPort;
    @Mock
    ScheduleLockPort scheduleLockPort;
    @Mock
    SchedulePersistencePort schedulePersistencePort;
    @InjectMocks
    ScheduleManagementFacade scheduleManagement;

    static final LocalDate START_DATE = LocalDate.of(2024, 1, 1);

    static SortedSet<ReservationSlot> someReservationSlots() {
        return new TreeSet<>(List.of(new ReservationSlot(LocalTime.NOON, LocalTime.MIDNIGHT, 1, 1)));
    }

    static SchedulePattern fiveTwoPattern() {
        return new SchedulePattern(1L, 5, 2, someReservationSlots());
    }

    static ScheduleExceptionDay someExceptionDay() {
        return new ScheduleExceptionDay(
                1L,
                START_DATE,
                someStaff(),
                someService(),
                someReservationSlots()
        );
    }

    static Business someBusiness() {
        return new Business(1L, "owner", true);
    }

    static Service someService() {
        return new Service(1L, someBusiness(), true);
    }

    static Staff someStaff() {
        return new Staff(1L, someBusiness(), "sub", true);
    }

    static Schedule twoOfFiveTwosSchedule(long daysToAdd) {
        return new Schedule(
                1L,
                START_DATE,
                START_DATE.plusDays(daysToAdd),
                someStaff(),
                someService(),
                new ArrayList<>(List.of(fiveTwoPattern(), fiveTwoPattern())),
                true
        );
    }

    @Test
    void givenValidScheduleChangeCommand_whenChangeSchedulePatterns_thenSchedulePatternsChanged() {
        // Given
        // Duration = 14
        ChangeSchedulePatternsCommand command = new ChangeSchedulePatternsCommand(
                1,
                List.of(fiveTwoPattern(), fiveTwoPattern())
        );
        // Duration = 15
        Schedule schedule = spy(twoOfFiveTwosSchedule(15));

        doReturn(Collections.emptyNavigableMap())
                .when(reservationRetrievalPort)
                .findAllActiveByStaff(anyLong(), any(LocalDate.class), any(LocalDate.class), eq(true));
        doReturn(schedule)
                .when(scheduleRetrievalPort)
                .findById(eq(command.scheduleId()), eq(true));

        // When
        scheduleManagement.changeSchedulePatterns(command);

        // Then
        verify(schedule).setPatterns(anyList());
        verify(schedulePersistencePort).save(same(schedule));
        verify(scheduleLockPort).lock(eq(command.scheduleId()), eq(false));
        verify(scheduleLockPort).lock(eq(schedule.getStaff().getId()), eq(false));
    }

    @Test
    void givenValidSchedulePatternsChangeCommand_butReservationsExist_whenChangeSchedulePatterns_thenThrowsIllegalStateException() {
        // Given
        // Duration = 14
        ChangeSchedulePatternsCommand command = new ChangeSchedulePatternsCommand(
                1,
                List.of(fiveTwoPattern(), fiveTwoPattern())
        );
        // Duration = 15
        Schedule schedule = spy(twoOfFiveTwosSchedule(15));

        doReturn(new TreeMap<>(Map.of(LocalDate.now(), List.of())))
                .when(reservationRetrievalPort)
                .findAllActiveByStaff(anyLong(), any(LocalDate.class), any(LocalDate.class), eq(true));
        doReturn(schedule)
                .when(scheduleRetrievalPort)
                .findById(eq(command.scheduleId()), eq(true));

        // When & Then
        assertThatThrownBy(() -> scheduleManagement.changeSchedulePatterns(command))
                .isInstanceOf(IllegalStateException.class);

        // Then
        verifyNoMoreInteractions(schedulePersistencePort);
        verify(scheduleLockPort).lock(eq(command.scheduleId()), eq(false));
        verify(scheduleLockPort).lock(eq(schedule.getStaff().getId()), eq(false));
    }

    @Test
    void givenInsufficientScheduleSize_whenChangeSchedulePatterns_thenIllegalStateExceptionThrown() {
        // Given
        // Duration = 14
        ChangeSchedulePatternsCommand command = new ChangeSchedulePatternsCommand(
                1,
                List.of(fiveTwoPattern(), fiveTwoPattern(), fiveTwoPattern())
        );
        // Duration = 13
        Schedule schedule = twoOfFiveTwosSchedule(15);

        doReturn(schedule)
                .when(scheduleRetrievalPort)
                .findById(eq(command.scheduleId()), eq(true));

        // When & Then
        assertThatThrownBy(() -> scheduleManagement.changeSchedulePatterns(command))
                .isInstanceOf(IllegalStateException.class);
        verifyNoMoreInteractions(schedulePersistencePort);
        verify(scheduleLockPort).lock(eq(command.scheduleId()), eq(false));
        verify(scheduleLockPort).lock(eq(schedule.getStaff().getId()), eq(false));
    }

    @Test
    void givenSmallerDurationAndPatternsExceed_thenThrowException() {
        // Given
        // Duration = 13
        ChangeScheduleDurationCommand command = new ChangeScheduleDurationCommand(
                1,
                START_DATE,
                START_DATE.plusDays(12)
        );
        // Duration = 99
        Schedule schedule = twoOfFiveTwosSchedule(99);

        doReturn(schedule)
                .when(scheduleRetrievalPort)
                .findById(eq(command.getScheduleId()), eq(true));

        // When & Then
        assertThatThrownBy(() -> scheduleManagement.changeScheduleDuration(command))
                .isInstanceOf(IllegalStateException.class);

        verifyNoMoreInteractions(schedulePersistencePort);
        verify(scheduleLockPort).lock(eq(command.getScheduleId()), eq(false));
        verify(scheduleLockPort).lock(eq(schedule.getStaff().getId()), eq(false));
    }

    @Test
    void givenSmallerDuration_andPatternsDoNotExceed_thenSucceedNoOverlapCheck() {
        // Given
        // Duration = 15
        ChangeScheduleDurationCommand command = new ChangeScheduleDurationCommand(
                1,
                START_DATE,
                START_DATE.plusDays(15)
        );
        // Duration = 99
        Schedule schedule = spy(twoOfFiveTwosSchedule(99));

        doReturn(Collections.emptyNavigableMap())
                .when(reservationRetrievalPort)
                .findAllActiveByStaff(anyLong(), any(LocalDate.class), any(LocalDate.class), eq(true));
        doReturn(schedule)
                .when(scheduleRetrievalPort)
                .findById(eq(command.getScheduleId()), eq(true));

        // When
        scheduleManagement.changeScheduleDuration(command);

        // Then
        verify(schedule).setStart(command.getStart());
        verify(schedule).setEnd(command.getEnd());
        verify(schedulePersistencePort).save(schedule);
        verify(scheduleLockPort).lock(eq(command.getScheduleId()), eq(false));
        verify(scheduleLockPort).lock(eq(schedule.getStaff().getId()), eq(false));
    }

    @Test
    void givenValidDurationChangeCommand_butReservationsExist_thenThrowsIllegalStateException() {
        // Given
        // Duration = 15
        ChangeScheduleDurationCommand command = new ChangeScheduleDurationCommand(
                1,
                START_DATE,
                START_DATE.plusDays(15)
        );
        // Duration = 99
        Schedule schedule = spy(twoOfFiveTwosSchedule(99));

        doReturn(new TreeMap<>(Map.of(LocalDate.now(), List.of())))
                .when(reservationRetrievalPort)
                .findAllActiveByStaff(anyLong(), any(LocalDate.class), any(LocalDate.class), eq(true));
        doReturn(schedule)
                .when(scheduleRetrievalPort)
                .findById(eq(command.getScheduleId()), eq(true));

        // When & Then
        assertThatThrownBy(() -> scheduleManagement.changeScheduleDuration(command))
                .isInstanceOf(IllegalStateException.class);

        // Then
        verifyNoMoreInteractions(schedulePersistencePort);
        verify(scheduleLockPort).lock(eq(command.getScheduleId()), eq(false));
        verify(scheduleLockPort).lock(eq(schedule.getStaff().getId()), eq(false));
    }

    @Test
    void givenBiggerDurationAndNoOverlap_thenSucceed() {
        // Given
        // Duration = 15
        ChangeScheduleDurationCommand command = new ChangeScheduleDurationCommand(
                1,
                START_DATE,
                START_DATE.plusDays(15)
        );
        // Duration = 14
        Schedule schedule = spy(twoOfFiveTwosSchedule(14));

        doReturn(Collections.emptyNavigableMap())
                .when(reservationRetrievalPort)
                .findAllActiveByStaff(anyLong(), any(LocalDate.class), any(LocalDate.class), eq(true));
        doReturn(schedule)
                .when(scheduleRetrievalPort)
                .findById(eq(command.getScheduleId()), eq(true));
        doReturn(Collections.emptyList())
                .when(scheduleRetrievalPort)
                .findActiveSchedulesByStaffAndService(anyLong(), anyLong(), any(), any());

        // When
        scheduleManagement.changeScheduleDuration(command);

        // Then
        verify(schedule).setStart(command.getStart());
        verify(schedule).setEnd(command.getEnd());
        verify(schedulePersistencePort).save(schedule);
        verify(scheduleLockPort).lockByStaff(anyLong(), eq(false));
        verify(scheduleLockPort).lock(anyLong(), eq(false));
    }

    @Test
    void givenBiggerDurationAndOverlap_thenThrowException() {
        // Given
        // Duration = 15
        ChangeScheduleDurationCommand command = new ChangeScheduleDurationCommand(
                1,
                START_DATE,
                START_DATE.plusDays(15)
        );
        // Duration = 14
        Schedule schedule = spy(twoOfFiveTwosSchedule(14));

        doReturn(Collections.emptyNavigableMap())
                .when(reservationRetrievalPort)
                .findAllActiveByStaff(anyLong(), any(LocalDate.class), any(LocalDate.class), eq(true));
        doReturn(schedule)
                .when(scheduleRetrievalPort)
                .findById(eq(command.getScheduleId()), eq(true));
        doReturn(List.of(twoOfFiveTwosSchedule(100), twoOfFiveTwosSchedule(100)))
                .when(scheduleRetrievalPort)
                .findActiveSchedulesByStaffAndService(anyLong(), anyLong(), any(), any());

        // When
        assertThatThrownBy(() -> scheduleManagement.changeScheduleDuration(command))
                .isInstanceOf(IllegalArgumentException.class);

        // Then
        verifyNoMoreInteractions(schedulePersistencePort);
        verify(schedule, never()).setStart(command.getStart());
        verify(schedule, never()).setEnd(command.getEnd());
        verify(scheduleLockPort).lockByStaff(anyLong(), eq(false));
        verify(scheduleLockPort).lock(anyLong(), eq(false));
    }

    @Test
    void givenBiggerDurationAndOverlapsWithPersistedSelf_thenSucceed() {
        // Given
        // Duration = 15
        ChangeScheduleDurationCommand command = new ChangeScheduleDurationCommand(
                1,
                START_DATE,
                START_DATE.plusDays(15)
        );
        // Duration = 14
        Schedule schedule = spy(twoOfFiveTwosSchedule(14));

        doReturn(Collections.emptyNavigableMap())
                .when(reservationRetrievalPort)
                .findAllActiveByStaff(anyLong(), any(LocalDate.class), any(LocalDate.class), eq(true));
        doReturn(schedule)
                .when(scheduleRetrievalPort)
                .findById(eq(command.getScheduleId()), eq(true));
        doReturn(List.of(schedule))
                .when(scheduleRetrievalPort)
                .findActiveSchedulesByStaffAndService(anyLong(), anyLong(), any(), any());

        // When
        scheduleManagement.changeScheduleDuration(command);

        // Then
        verify(schedule).setStart(command.getStart());
        verify(schedule).setEnd(command.getEnd());
        verify(schedulePersistencePort).save(schedule);
        verify(scheduleLockPort).lockByStaff(anyLong(), eq(false));
        verify(scheduleLockPort).lock(anyLong(), eq(false));
    }

    @Test
    void givenValidCreateScheduleCommand_thenScheduleCreated() {
        // Given
        CreateScheduleCommand command = new CreateScheduleCommand(
                1,
                1,
                START_DATE,
                START_DATE.plusDays(15),
                List.of(fiveTwoPattern(), fiveTwoPattern())
        );
        Staff staff = someStaff();
        Service service = someService();

        doReturn(staff)
                .when(staffRetrievalPort)
                .findActiveById(eq(command.getStaffId()));
        doReturn(service)
                .when(serviceRetrievalPort)
                .findActiveById(eq(command.getServiceId()));
        doReturn(Collections.emptyList())
                .when(scheduleRetrievalPort)
                .findActiveSchedulesByStaffAndService(eq(command.getStaffId()), eq(command.getServiceId()), any(), any());

        // When
        scheduleManagement.createSchedule(command);

        // Then
        verify(schedulePersistencePort).save(any(Schedule.class));
        verify(scheduleLockPort).lockByStaff(eq(command.getStaffId()), eq(false));
    }

    @Test
    void givenInvalidDurationForPatterns_whenCreateSchedule_thenIllegalStateExceptionThrown() {
        // Given
        CreateScheduleCommand command = new CreateScheduleCommand(
                1,
                1,
                START_DATE,
                START_DATE.plusDays(12),
                List.of(fiveTwoPattern(), fiveTwoPattern())
        );

        // When & Then
        assertThatThrownBy(() -> scheduleManagement.createSchedule(command))
                .isInstanceOf(IllegalStateException.class);
        verifyNoMoreInteractions(schedulePersistencePort);
        verify(scheduleLockPort).lockByStaff(eq(command.getStaffId()), eq(false));
    }

    @Test
    void givenOverlapWithExistingSchedules_whenCreateSchedule_thenIllegalStateExceptionThrown() {
        // Given
        CreateScheduleCommand command = new CreateScheduleCommand(
                1,
                1,
                START_DATE,
                START_DATE.plusDays(13),
                List.of(fiveTwoPattern(), fiveTwoPattern())
        );

        doReturn(List.of(twoOfFiveTwosSchedule(100)))
                .when(scheduleRetrievalPort)
                .findActiveSchedulesByStaffAndService(eq(command.getStaffId()), eq(command.getServiceId()), any(), any());

        // When & Then
        assertThatThrownBy(() -> scheduleManagement.createSchedule(command))
                .isInstanceOf(IllegalStateException.class);
        verifyNoMoreInteractions(schedulePersistencePort);
        verify(scheduleLockPort).lockByStaff(eq(command.getStaffId()), eq(false));
    }

    @Test
    void givenValidCreateScheduleExceptionDayCommand_thenExceptionDayAddedOrUpdated() {
        // Given
        CreateScheduleExceptionDayCommand command = new CreateScheduleExceptionDayCommand(
                1,
                1,
                START_DATE,
                someReservationSlots()
        );
        Staff staff = someStaff();
        Service service = someService();

        doReturn(Collections.emptyNavigableMap())
                .when(reservationRetrievalPort)
                .findAllActiveByStaff(anyLong(), any(LocalDate.class), any(LocalDate.class), eq(true));
        doReturn(staff)
                .when(staffRetrievalPort)
                .findActiveById(eq(command.staffId()));
        doReturn(service)
                .when(serviceRetrievalPort)
                .findActiveById(eq(command.serviceId()));
        doAnswer(call -> call.getArgument(0))
                .when(schedulePersistencePort)
                .save(any(ScheduleExceptionDay.class));
        doReturn(Optional.empty())
                .when(scheduleRetrievalPort)
                .findActiveExceptionDayByStaffAndService(eq(command.staffId()), eq(command.serviceId()), eq(command.date()));

        // When
        ScheduleExceptionDay added = scheduleManagement.addOrUpdateScheduleExceptionDay(command);

        // Then
        assertThat(added.getStaff()).isSameAs(staff);
        assertThat(added.getService()).isSameAs(service);
        assertThat(added.getDate()).isEqualTo(command.date());
        assertThat(added.getReservationSlots()).isEqualTo(command.reservationSlots());
        verify(schedulePersistencePort).save(any(ScheduleExceptionDay.class));
        verify(scheduleLockPort).lockByStaff(eq(command.staffId()), eq(false));
    }

    @Test
    void givenValidCreateScheduleExceptionDayCommand_butReservationsExist_thenThrowsIllegalStateException() {
        // Given
        CreateScheduleExceptionDayCommand command = new CreateScheduleExceptionDayCommand(
                1,
                1,
                START_DATE,
                someReservationSlots()
        );
        Staff staff = someStaff();
        Service service = someService();

        doReturn(new TreeMap<>(Map.of(LocalDate.now(), List.of())))
                .when(reservationRetrievalPort)
                .findAllActiveByStaff(anyLong(), any(LocalDate.class), any(LocalDate.class), eq(true));
        doReturn(staff)
                .when(staffRetrievalPort)
                .findActiveById(eq(command.staffId()));
        doReturn(service)
                .when(serviceRetrievalPort)
                .findActiveById(eq(command.serviceId()));
        doReturn(Optional.empty())
                .when(scheduleRetrievalPort)
                .findActiveExceptionDayByStaffAndService(eq(command.staffId()), eq(command.serviceId()), eq(command.date()));


        // When & Then
        assertThatThrownBy(() -> scheduleManagement.addOrUpdateScheduleExceptionDay(command))
                .isInstanceOf(IllegalStateException.class);

        // Then
        verifyNoMoreInteractions(schedulePersistencePort);
        verify(scheduleLockPort).lockByStaff(eq(command.staffId()), eq(false));
    }

    @Test
    void givenExistingExceptionDay_whenAddOrUpdateScheduleExceptionDay_thenExceptionDayUpdated() {
        // Given
        CreateScheduleExceptionDayCommand command = new CreateScheduleExceptionDayCommand(
                1,
                1,
                START_DATE,
                someReservationSlots()
        );
        Staff staff = someStaff();
        Service service = someService();
        ScheduleExceptionDay exceptionDay = someExceptionDay();

        doReturn(Collections.emptyNavigableMap())
                .when(reservationRetrievalPort)
                .findAllActiveByStaff(anyLong(), any(LocalDate.class), any(LocalDate.class), eq(true));
        doReturn(staff)
                .when(staffRetrievalPort)
                .findActiveById(eq(command.staffId()));
        doReturn(service)
                .when(serviceRetrievalPort)
                .findActiveById(eq(command.serviceId()));
        doReturn(Optional.of(exceptionDay))
                .when(scheduleRetrievalPort)
                .findActiveExceptionDayByStaffAndService(eq(command.staffId()), eq(command.serviceId()), eq(command.date()));

        // When
        scheduleManagement.addOrUpdateScheduleExceptionDay(command);

        // Then
        assertThat(exceptionDay.getReservationSlots()).isEqualTo(command.reservationSlots());
        verify(schedulePersistencePort).save(same(exceptionDay));
    }

    @Test
    void givenExistingSchedule_whenRemoveSchedule_thenScheduleDeactivated() {
        // Given
        RemoveScheduleCommand command = new RemoveScheduleCommand(1);
        Schedule schedule = spy(twoOfFiveTwosSchedule(1));

        doReturn(Collections.emptyNavigableMap())
                .when(reservationRetrievalPort)
                .findAllActiveByStaff(anyLong(), any(LocalDate.class), any(LocalDate.class), eq(true));
        doReturn(schedule)
                .when(scheduleRetrievalPort)
                .findById(anyLong(), eq(true));

        // When
        scheduleManagement.removeSchedule(command);

        // Then
        assertThat(schedule.isActive()).isFalse();
        verify(schedule).setActive(eq(false));
        verify(schedulePersistencePort).save(same(schedule));
    }

    @Test
    void givenExistingSchedule_butReservationsExist_thenThrowsIllegalStateException() {
        // Given
        RemoveScheduleCommand command = new RemoveScheduleCommand(1);
        Schedule schedule = spy(twoOfFiveTwosSchedule(1));

        doReturn(new TreeMap<>(Map.of(LocalDate.now(), List.of())))
                .when(reservationRetrievalPort)
                .findAllActiveByStaff(anyLong(), any(LocalDate.class), any(LocalDate.class), eq(true));
        doReturn(schedule)
                .when(scheduleRetrievalPort)
                .findById(anyLong(), eq(true));

        // When & Then
        assertThatThrownBy(() -> scheduleManagement.removeSchedule(command))
                .isInstanceOf(IllegalStateException.class);

        // Then
        verifyNoMoreInteractions(schedulePersistencePort);
        verify(scheduleLockPort).lock(eq(command.scheduleId()), eq(false));
        verify(scheduleLockPort).lockByStaff(eq(schedule.getStaff().getId()), eq(false));
    }
}