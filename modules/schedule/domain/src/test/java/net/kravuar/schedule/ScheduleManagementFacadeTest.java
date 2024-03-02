package net.kravuar.schedule;

import net.kravuar.schedule.domain.Schedule;
import net.kravuar.schedule.domain.SchedulePattern;
import net.kravuar.schedule.domain.commands.ChangeSchedulePatternsCommand;
import net.kravuar.schedule.ports.out.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ScheduleManagementFacadeTest {
    @Mock
    ScheduleRetrievalPort scheduleRetrievalPort;
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

    @Test
    void givenValidScheduleChangeCommand_whenChangeSchedulePatterns_thenSchedulePatternsChanged() {
        // Given
        // Duration = 14
        SchedulePattern schedulePattern = mock(SchedulePattern.class);
        doReturn(5)
                .when(schedulePattern)
                .getRepeatDays();
        doReturn(2)
                .when(schedulePattern)
                .getPauseDays();
        List<SchedulePattern> patterns = Arrays.asList(
                schedulePattern,
                schedulePattern
        );
        ChangeSchedulePatternsCommand command = new ChangeSchedulePatternsCommand(
                1,
                patterns
        );
        Schedule schedule = mock(Schedule.class);

        doReturn(schedule)
                .when(scheduleRetrievalPort)
                .findById(eq(command.scheduleId()), eq(true));
        doReturn(schedule)
                .when(schedulePersistencePort)
                .save(same(schedule));
        // Duration = 15
        doReturn(LocalDate.of(2024, 1, 1))
                .when(schedule)
                .getStart();
        doReturn(LocalDate.of(2024, 1, 15))
                .when(schedule)
                .getEnd();

        // When
        Schedule changedSchedule = scheduleManagement.changeSchedulePatterns(command);

        // Then
        verify(scheduleLockPort, times(1)).lock(eq(command.scheduleId()), eq(true));
        verify(scheduleRetrievalPort, times(1)).findById(eq(command.scheduleId()), eq(true));
        verify(schedule, times(1)).setPatterns(same(patterns));
        verify(schedulePersistencePort, times(1)).save(eq(schedule));
        verify(scheduleLockPort, times(1)).lock(eq(command.scheduleId()), eq(false));
        assertThat(changedSchedule).isEqualTo(schedule);
    }

    @Test
    void givenInsufficientScheduleSize_whenChangeSchedulePatterns_thenIllegalStateExceptionThrown() {
        // Given
        // Duration = 14
        SchedulePattern schedulePattern = mock(SchedulePattern.class);
        doReturn(5)
                .when(schedulePattern)
                .getRepeatDays();
        doReturn(2)
                .when(schedulePattern)
                .getPauseDays();
        List<SchedulePattern> patterns = Arrays.asList(
                schedulePattern,
                schedulePattern
        );
        ChangeSchedulePatternsCommand command = new ChangeSchedulePatternsCommand(
                1,
                patterns
        );
        Schedule schedule = mock(Schedule.class);

        doReturn(schedule)
                .when(scheduleRetrievalPort)
                .findById(eq(command.scheduleId()), eq(true));
        // Duration = 13
        doReturn(LocalDate.of(2024, 1, 1))
                .when(schedule)
                .getStart();
        doReturn(LocalDate.of(2024, 1, 13))
                .when(schedule)
                .getEnd();

        // When & Then
        assertThatThrownBy(() -> scheduleManagement.changeSchedulePatterns(command))
                .isInstanceOf(IllegalStateException.class);
        verify(scheduleLockPort, times(1)).lock(eq(command.scheduleId()), eq(true));
        verify(scheduleRetrievalPort, times(1)).findById(eq(command.scheduleId()), eq(true));
        verify(schedulePersistencePort, never()).save(any(Schedule.class));
        verify(scheduleLockPort, times(1)).lock(eq(command.scheduleId()), eq(false));
    }
}