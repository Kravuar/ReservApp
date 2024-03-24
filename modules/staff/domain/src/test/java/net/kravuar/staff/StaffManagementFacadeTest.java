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
    AccountRetrievalPort accountRetrievalPort;
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

    static Staff someStaff() {
        return new Staff(1L, "sub", someBusiness(), true, "");
    }

    static Business someBusiness() {
        return new Business(1L, "owner", true);
    }

    static StaffInvitation someInvitation() {
        return new StaffInvitation(1L, "sub", someBusiness(), StaffInvitation.Status.WAITING);
    }

    @Test
    void givenValidStaffInvitationCommand_whenSendInvitation_thenInvitationSent() {
        // Given
        StaffInvitationCommand command = new StaffInvitationCommand(
                "sub",
                1
        );
        Business business = someBusiness();

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
                .when(accountRetrievalPort)
                .exists(eq(command.sub()));
        doReturn(mock(StaffInvitation.class))
                .when(invitationPersistencePort)
                .save(any(StaffInvitation.class));

        // When
        StaffInvitation invitation = staffManagement.sendInvitation(command);

        // Then
        verify(staffLockPort).lock(eq(command.businessId()), eq(command.sub()), eq(false));
        verify(invitationPersistencePort).save(any(StaffInvitation.class));
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
                .isInstanceOf(IllegalStateException.class);
        verify(staffLockPort).lock(eq(command.businessId()), eq(command.sub()), eq(false));
        verifyNoInteractions(invitationRetrievalPort, accountRetrievalPort, invitationPersistencePort);
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
                .isInstanceOf(IllegalStateException.class);
        verify(staffLockPort).lock(eq(command.businessId()), eq(command.sub()), eq(false));
        verifyNoInteractions(accountRetrievalPort, invitationPersistencePort);
    }

    @Test
    void givenNonExistingAccount_whenSendInvitation_thenAccountNotFoundExceptionThrown() {
        // Given
        StaffInvitationCommand command = new StaffInvitationCommand(
                "sub",
                1
        );

        doReturn(false)
                .when(accountRetrievalPort)
                .exists(eq(command.sub()));

        // When & Then
        assertThatThrownBy(() -> staffManagement.sendInvitation(command))
                .isInstanceOf(AccountNotFoundException.class);
        verify(staffLockPort).lock(eq(command.businessId()), eq(command.sub()), eq(false));
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
        StaffInvitation invitation = spy(someInvitation());
        Staff staff = someStaff(); // Existing staff (removed previously)

        doReturn(invitation)
                .when(invitationRetrievalPort)
                .findById(eq(command.invitationId()));
        if (command.accept())
            doReturn(Optional.of(staff))
                    .when(staffRetrievalPort)
                    .findByBusinessAndSub(
                            anyLong(),
                            anyString(),
                            eq(false),
                            eq(false)
                    );

        // When
        staffManagement.answerInvitation(command);

        // Then
        verify(invitation).getStatus();
        verify(invitation).setStatus(argThat(status ->
                status == (command.accept()
                        ? StaffInvitation.Status.ACCEPTED
                        : StaffInvitation.Status.DECLINED)
        ));
        verify(staffLockPort).lock(anyLong(), anyString(), eq(false));
        verify(invitationPersistencePort).save(same(invitation));
        if (command.accept()) {
            verify(staffPersistencePort).save(any(Staff.class));
            verify(staffNotificationPort).notifyNewStaff(any(Staff.class));
        } else {
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
        StaffInvitation invitation = someInvitation();
        invitation.setStatus(StaffInvitation.Status.ACCEPTED);

        doReturn(invitation)
                .when(invitationRetrievalPort)
                .findById(eq(command.invitationId()));

        // When & Then
        assertThatThrownBy(() -> staffManagement.answerInvitation(command))
                .isInstanceOf(InvitationInvalidStatusException.class);
        verify(invitationRetrievalPort).findById(eq(command.invitationId()));
        verify(staffLockPort).lock(anyLong(), anyString(), eq(false));
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
        Staff staff = spy(someStaff());

        doReturn(staff)
                .when(staffRetrievalPort)
                .findById(eq(command.staffId()), eq(true));

        // When
        staffManagement.changeDetails(command);

        // Then
        verify(staffLockPort).lock(eq(command.staffId()), eq(false));
        verify(staffPersistencePort).save(eq(staff));
        if (command.description() != null)
            verify(staff).setDescription(eq(command.description()));
        else
            verify(staff, never()).setDescription(anyString());
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
        verify(staffLockPort).lock(eq(command.staffId()), eq(false));
        verify(staffPersistencePort, never()).save(any(Staff.class));
    }

    @Test
    void givenValidRemoveStaffCommand_whenRemoveStaff_thenStaffRemoved() {
        // Given
        RemoveStaffCommand command = new RemoveStaffCommand(1);
        Staff staff = spy(someStaff());

        doReturn(staff)
                .when(staffRetrievalPort)
                .findById(eq(command.staffId()), eq(true));

        // When
        staffManagement.removeStaff(command);

        // Then
        verify(staff).setActive(eq(false));
        verify(staffPersistencePort).save(eq(staff));
        verify(staffNotificationPort).notifyStaffActiveChanged(eq(staff));
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
        verifyNoInteractions(staffPersistencePort, staffNotificationPort);
    }
}