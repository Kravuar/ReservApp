package net.kravuar.staff;

import net.kravuar.staff.domain.Business;
import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.domain.StaffInvitation;
import net.kravuar.staff.domain.commands.RemoveStaffCommand;
import net.kravuar.staff.domain.commands.StaffAnswerInvitationCommand;
import net.kravuar.staff.domain.commands.StaffChangeDetailsCommand;
import net.kravuar.staff.domain.commands.StaffInvitationCommand;
import net.kravuar.staff.domain.exceptions.AccountNotFoundException;
import net.kravuar.staff.domain.exceptions.InvitationInvalidStatusException;
import net.kravuar.staff.domain.exceptions.InvitationNotFoundException;
import net.kravuar.staff.domain.exceptions.StaffNotFoundException;
import net.kravuar.staff.ports.out.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StaffManagementFacadeTest {
    @Mock
    AccountExistenceCheckPort accountExistenceCheckPort;
    @Mock
    StaffPersistencePort staffPersistencePort;
    @Mock
    StaffRetrievalPort staffRetrievalPort;
    @Mock
    BusinessRetrievalPort businessRetrievalPort;
    @Mock
    StaffNotificationPort staffNotificationPort;
    @Mock
    StaffLockPort staffLockPort;
    @Mock
    InvitationPersistencePort invitationPersistencePort;
    @Mock
    InvitationRetrievalPort invitationRetrievalPort;
    @InjectMocks
    StaffManagementFacade staffManagement;

    @Test
    void givenValidStaffInvitationCommand_whenSendInvitation_thenInvitationSent() {
        // Given
        StaffInvitationCommand command = new StaffInvitationCommand(
                "sub",
                1
        );
        Business business = mock(Business.class);

        doReturn(business)
                .when(businessRetrievalPort)
                .findById(eq(command.businessId()));
        doReturn(false)
                .when(staffRetrievalPort)
                .existsActiveByBusinessAndSub(eq(command.businessId()), eq(command.sub()));
        doReturn(false)
                .when(invitationRetrievalPort)
                .existsWaitingByBusinessAndSub(eq(command.businessId()), eq(command.sub()));
        doReturn(true)
                .when(accountExistenceCheckPort)
                .exists(eq(command.sub()));
        doReturn(mock(StaffInvitation.class))
                .when(invitationPersistencePort)
                .save(any(StaffInvitation.class));

        // When
        StaffInvitation invitation = staffManagement.sendInvitation(command);

        // Then
        verify(staffLockPort, times(1)).lock(eq(command.businessId()), eq(command.sub()), eq(true));
        verify(staffLockPort, times(1)).lock(eq(command.businessId()), eq(command.sub()), eq(false));
        verify(businessRetrievalPort, times(1)).findById(eq(command.businessId()));
        verify(staffRetrievalPort, times(1)).existsActiveByBusinessAndSub(eq(command.businessId()), eq(command.sub()));
        verify(invitationRetrievalPort, times(1)).existsWaitingByBusinessAndSub(eq(command.businessId()), eq(command.sub()));
        verify(accountExistenceCheckPort, times(1)).exists(eq(command.sub()));
        verify(invitationPersistencePort, times(1)).save(any(StaffInvitation.class));
        assertThat(invitation).isNotNull();
    }

    @Test
    void givenExistingStaff_whenSendInvitation_thenStaffAlreadyExistsExceptionThrown() {
        // Given
        StaffInvitationCommand command = new StaffInvitationCommand(
                "sub",
                1
        );

        doReturn(true)
                .when(staffRetrievalPort)
                .existsActiveByBusinessAndSub(eq(command.businessId()), eq(command.sub()));

        // When & Then
        assertThatThrownBy(() -> staffManagement.sendInvitation(command))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Staff already exists");
        verify(staffLockPort, times(1)).lock(eq(command.businessId()), eq(command.sub()), eq(true));
        verify(staffLockPort, times(1)).lock(eq(command.businessId()), eq(command.sub()), eq(false));
        verify(staffRetrievalPort, times(1)).existsActiveByBusinessAndSub(eq(command.businessId()), eq(command.sub()));
        verify(businessRetrievalPort, times(1)).findById(eq(command.businessId()));
        verifyNoInteractions(invitationRetrievalPort, accountExistenceCheckPort, invitationPersistencePort);
    }

    @Test
    void givenExistingInvitation_whenSendInvitation_thenInvitationAlreadyExistsExceptionThrown() {
        // Given
        StaffInvitationCommand command = new StaffInvitationCommand(
                "sub",
                1
        );

        doReturn(true)
                .when(invitationRetrievalPort)
                .existsWaitingByBusinessAndSub(eq(command.businessId()), eq(command.sub()));

        // When & Then
        assertThatThrownBy(() -> staffManagement.sendInvitation(command))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Invitation already exists");
        verify(staffLockPort, times(1)).lock(eq(command.businessId()), eq(command.sub()), eq(true));
        verify(staffLockPort, times(1)).lock(eq(command.businessId()), eq(command.sub()), eq(false));
        verify(invitationRetrievalPort, times(1)).existsWaitingByBusinessAndSub(eq(command.businessId()), eq(command.sub()));
        verify(staffRetrievalPort, times(1)).existsActiveByBusinessAndSub(eq(command.businessId()), eq(command.sub()));
        verify(businessRetrievalPort, times(1)).findById(eq(command.businessId()));
        verifyNoInteractions(accountExistenceCheckPort, invitationPersistencePort);
    }

    @Test
    void givenNonExistingAccount_whenSendInvitation_thenAccountNotFoundExceptionThrown() {
        // Given
        StaffInvitationCommand command = new StaffInvitationCommand(
                "sub",
                1
        );

        doReturn(false)
                .when(accountExistenceCheckPort)
                .exists(eq(command.sub()));

        // When & Then
        assertThatThrownBy(() -> staffManagement.sendInvitation(command))
                .isInstanceOf(AccountNotFoundException.class);
        verify(staffLockPort, times(1)).lock(eq(command.businessId()), eq(command.sub()), eq(true));
        verify(staffLockPort, times(1)).lock(eq(command.businessId()), eq(command.sub()), eq(false));
        verify(accountExistenceCheckPort, times(1)).exists(eq(command.sub()));
        verify(invitationRetrievalPort, times(1)).existsWaitingByBusinessAndSub(eq(command.businessId()), eq(command.sub()));
        verify(staffRetrievalPort, times(1)).existsActiveByBusinessAndSub(eq(command.businessId()), eq(command.sub()));
        verify(businessRetrievalPort, times(1)).findById(eq(command.businessId()));
        verifyNoInteractions(invitationPersistencePort);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void givenValidAnswerInvitationCommand_whenAnswerInvitation_thenInvitationAnswered(boolean accept) {
        // Given
        StaffAnswerInvitationCommand command = new StaffAnswerInvitationCommand(
                1,
                accept
        );
        StaffInvitation invitation = mock(StaffInvitation.class);
        Business business = mock(Business.class);
        long businessId = 1;
        String sub = "sub";
        Staff staff = mock(Staff.class); // Existing staff (removed previously)

        doReturn(businessId)
                .when(business)
                .getId();
        doReturn(invitation)
                .when(invitationRetrievalPort)
                .findById(eq(command.invitationId()));
        doReturn(business)
                .when(invitation)
                .getBusiness();
        doReturn(sub)
                .when(invitation)
                .getSub();
        doReturn(StaffInvitation.Status.WAITING)
                .when(invitation)
                .getStatus();
        if (command.accept())
            doReturn(Optional.of(staff))
                    .when(staffRetrievalPort)
                    .findByBusinessAndSub(
                            eq(businessId),
                            eq(sub),
                            eq(false),
                            eq(false)
                    );

        // When
        staffManagement.answerInvitation(command);

        // Then
        verify(staffLockPort, times(1)).lock(
                eq(businessId),
                eq(sub),
                eq(true)
        );
        verify(staffLockPort, times(1)).lock(
                eq(businessId),
                eq(sub),
                eq(false)
        );
        verify(invitation, times(1)).getStatus();
        verify(invitation, times(1)).setStatus(argThat(status ->
                status == (command.accept()
                        ? StaffInvitation.Status.ACCEPTED
                        : StaffInvitation.Status.DECLINED)
        ));
        verify(staffLockPort, times(1)).lock(eq(businessId), eq(sub), eq(true));
        verify(staffLockPort, times(1)).lock(eq(businessId), eq(sub), eq(false));
        verify(invitationRetrievalPort, times(1)).findById(eq(command.invitationId()));
        verify(invitationPersistencePort, times(1)).save(same(invitation));
        if (command.accept()) {
            verify(staff, times(1)).setActive(eq(true));
            verify(staffPersistencePort, times(1)).save(any(Staff.class));
            verify(staffNotificationPort, times(1)).notifyNewStaff(any(Staff.class));
        } else {
            verify(staffRetrievalPort, never()).findByBusinessAndSub(anyLong(), anyString(), eq(false), eq(false));
            verify(staffPersistencePort, never()).save(any(Staff.class));
            verify(staffNotificationPort, never()).notifyNewStaff(any(Staff.class));
        }
    }

    @Test
    void givenNonWaitingInvitation_whenAnswerInvitation_thenInvitationInvalidStatusExceptionThrown() {
        // Given
        StaffAnswerInvitationCommand command = new StaffAnswerInvitationCommand(
                1,
                true
        );
        StaffInvitation invitation = mock(StaffInvitation.class);
        Business business = mock(Business.class);
        long businessId = 1;
        String sub = "sub";

        doReturn(businessId)
                .when(business)
                .getId();
        doReturn(invitation)
                .when(invitationRetrievalPort)
                .findById(eq(command.invitationId()));
        doReturn(StaffInvitation.Status.ACCEPTED)
                .when(invitation)
                .getStatus();
        doReturn(business)
                .when(invitation)
                .getBusiness();
        doReturn(sub)
                .when(invitation)
                .getSub();

        // When & Then
        assertThatThrownBy(() -> staffManagement.answerInvitation(command))
                .isInstanceOf(InvitationInvalidStatusException.class);
        verify(staffLockPort, times(1)).lock(
                anyLong(),
                eq(sub),
                eq(true)
        );
        verify(staffLockPort, times(1)).lock(
                anyLong(),
                eq(sub),
                eq(false)
        );
        verify(staffLockPort, times(1)).lock(eq(businessId), eq(sub), eq(true));
        verify(staffLockPort, times(1)).lock(eq(businessId), eq(sub), eq(false));
        verify(invitationRetrievalPort, times(1)).findById(eq(command.invitationId()));
        verify(invitation, times(1)).getStatus();
        verifyNoInteractions(invitationPersistencePort, staffRetrievalPort, staffPersistencePort, staffNotificationPort);
    }

    @Test
    void givenNonExistingInvitation_whenAnswerInvitation_thenInvitationNotFoundExceptionThrown() {
        // Given
        StaffAnswerInvitationCommand command = new StaffAnswerInvitationCommand(
                -1,
                true
        );

        doThrow(InvitationNotFoundException.class)
                .when(invitationRetrievalPort)
                .findById(eq(command.invitationId()));

        // When & Then
        assertThatThrownBy(() -> staffManagement.answerInvitation(command))
                .isInstanceOf(InvitationNotFoundException.class);
        verifyNoInteractions(staffLockPort, invitationPersistencePort, staffRetrievalPort, staffPersistencePort, staffNotificationPort);
    }

    @ParameterizedTest
    @NullSource
    @CsvSource(value = {
            "New Description"
    })
    void givenValidStaffChangeDetailsCommand_whenChangeDetails_thenDetailsChanged(String description) {
        // Given
        StaffChangeDetailsCommand command = new StaffChangeDetailsCommand(
                1,
                description
        );
        Staff staff = mock(Staff.class);

        doReturn(staff)
                .when(staffRetrievalPort)
                .findById(eq(command.staffId()), eq(true));

        // When
        staffManagement.changeDetails(command);

        // Then
        verify(staffLockPort, times(1)).lock(eq(command.staffId()), eq(true));
        verify(staffLockPort, times(1)).lock(eq(command.staffId()), eq(false));
        verify(staffRetrievalPort, times(1)).findById(eq(command.staffId()), eq(true));
        verify(staffPersistencePort, times(1)).save(eq(staff));
        if (command.description() != null)
            verify(staff, times(1)).setDescription(eq(command.description()));
        else
            verify(staff, times(0)).setDescription(anyString());
    }

    @Test
    void givenNonExistingStaffId_whenChangeDetails_thenStaffNotFoundExceptionThrown() {
        // Given
        StaffChangeDetailsCommand command = new StaffChangeDetailsCommand(
                -1,
                "New Description"
        );

        doThrow(StaffNotFoundException.class)
                .when(staffRetrievalPort)
                .findById(eq(command.staffId()), eq(true));

        // When & Then
        assertThatThrownBy(() -> staffManagement.changeDetails(command))
                .isInstanceOf(StaffNotFoundException.class);
        verify(staffLockPort, times(1)).lock(eq(command.staffId()), eq(true));
        verify(staffLockPort, times(1)).lock(eq(command.staffId()), eq(false));
        verify(staffRetrievalPort, times(1)).findById(eq(command.staffId()), eq(true));
        verify(staffPersistencePort, never()).save(any(Staff.class));
    }

    @Test
    void givenValidRemoveStaffCommand_whenRemoveStaff_thenStaffRemoved() {
        // Given
        RemoveStaffCommand command = new RemoveStaffCommand(1);
        Staff staff = mock(Staff.class);

        doReturn(staff)
                .when(staffRetrievalPort)
                .findById(eq(command.staffId()), eq(true));

        // When
        staffManagement.removeStaff(command);

        // Then
        verify(staffRetrievalPort, times(1)).findById(eq(command.staffId()), eq(true));
        verify(staff, times(1)).setActive(eq(false));
        verify(staffPersistencePort, times(1)).save(eq(staff));
        verify(staffNotificationPort, times(1)).notifyStaffActiveChanged(eq(staff));
    }

    @Test
    void givenNonExistingStaff_whenRemoveStaff_thenStaffNotFoundExceptionThrown() {
        // Given
        RemoveStaffCommand command = new RemoveStaffCommand(-1);

        doThrow(StaffNotFoundException.class)
                .when(staffRetrievalPort)
                .findById(eq(command.staffId()), eq(true));

        // When & Then
        assertThatThrownBy(() -> staffManagement.removeStaff(command))
                .isInstanceOf(StaffNotFoundException.class);
        verify(staffRetrievalPort, times(1)).findById(eq(command.staffId()), eq(true));
        verifyNoInteractions(staffPersistencePort, staffNotificationPort);
    }
}