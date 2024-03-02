package net.kravuar.business;

import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.commands.BusinessChangeActiveCommand;
import net.kravuar.business.domain.commands.BusinessChangeDetailsCommand;
import net.kravuar.business.domain.commands.BusinessChangeNameCommand;
import net.kravuar.business.domain.commands.BusinessCreationCommand;
import net.kravuar.business.domain.exceptions.BusinessNameAlreadyTaken;
import net.kravuar.business.domain.exceptions.BusinessNotFoundException;
import net.kravuar.business.ports.out.BusinessLockPort;
import net.kravuar.business.ports.out.BusinessNotificationPort;
import net.kravuar.business.ports.out.BusinessPersistencePort;
import net.kravuar.business.ports.out.BusinessRetrievalPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BusinessManagementFacadeTest {
    @Mock
    BusinessPersistencePort businessPersistencePort;
    @Mock
    BusinessRetrievalPort businessRetrievalPort;
    @Mock
    BusinessNotificationPort businessNotificationPort;
    @Mock
    BusinessLockPort businessLockPort;
    @InjectMocks
    BusinessManagementFacade businessManagement;

    @Test
    void givenValidBusinessCreationCommand_whenCreate_thenNewBusinessCreated() {
        // Given
        BusinessCreationCommand command = new BusinessCreationCommand(
                "Owner",
                "New Business",
                "Description"
        );

        doReturn(false)
                .when(businessRetrievalPort)
                .existsActiveByName(eq(command.name()));
        doReturn(mock(Business.class))
                .when(businessPersistencePort)
                .save(any(Business.class));

        // When
        Business newBusiness = businessManagement.create(command);

        // Then
        verify(businessLockPort, times(1)).lock(eq(command.name()), eq(true));
        verify(businessLockPort, times(1)).lock(eq(command.name()), eq(false));
        verify(businessRetrievalPort, times(1)).existsActiveByName(eq(command.name()));
        verify(businessPersistencePort, times(1)).save(any(Business.class));
        verify(businessNotificationPort, times(1)).notifyNewBusiness(any(Business.class));
        assertThat(newBusiness).isNotNull();
    }

    @Test
    void givenExistingBusinessName_whenCreate_thenBusinessNameAlreadyTakenExceptionThrown() {
        // Given
        BusinessCreationCommand command = new BusinessCreationCommand(
                "Owner",
                "Existing Business",
                "Description"
        );

        doReturn(true)
                .when(businessRetrievalPort)
                .existsActiveByName(eq(command.name()));

        // When & Then
        assertThatThrownBy(() -> businessManagement.create(command))
                .isInstanceOf(BusinessNameAlreadyTaken.class);
        verify(businessRetrievalPort, times(1)).existsActiveByName(eq(command.name()));
        verify(businessPersistencePort, never()).save(any(Business.class));
        verify(businessNotificationPort, never()).notifyNewBusiness(any(Business.class));
        verify(businessLockPort, times(1)).lock(eq(command.name()), eq(true));
        verify(businessLockPort, times(1)).lock(eq(command.name()), eq(false));
    }

    @Test
    void givenValidBusinessChangeNameCommand_whenChangeName_thenNameChanged() {
        // Given
        BusinessChangeNameCommand command = new BusinessChangeNameCommand(
                1,
                "New Name"
        );
        Business business = mock(Business.class);

        doReturn(business)
                .when(businessRetrievalPort)
                .findById(eq(command.businessId()), eq(false));
        doReturn(false)
                .when(businessRetrievalPort)
                .existsActiveByName(eq(command.newName()));

        // When
        businessManagement.changeName(command);

        // Then
        verify(businessLockPort, times(1)).lock(eq(command.businessId()), eq(true));
        verify(businessLockPort, times(1)).lock(eq(command.newName()), eq(true));
        verify(businessRetrievalPort, times(1)).findById(eq(command.businessId()), eq(false));
        verify(businessRetrievalPort, times(1)).existsActiveByName(eq(command.newName()));
        verify(businessPersistencePort, times(1)).save(eq(business));
        verify(businessLockPort, times(1)).lock(eq(command.newName()), eq(false));
        verify(businessLockPort, times(1)).lock(eq(command.businessId()), eq(false));
    }

    @Test
    void givenExistingBusinessName_whenChangeName_thenBusinessNameAlreadyTakenExceptionThrown() {
        // Given
        BusinessChangeNameCommand command = new BusinessChangeNameCommand(
                1,
                "Existing Name"
        );

        doReturn(true)
                .when(businessRetrievalPort)
                .existsActiveByName(eq(command.newName()));

        // When & Then
        assertThatThrownBy(() -> businessManagement.changeName(command))
                .isInstanceOf(BusinessNameAlreadyTaken.class);
        verify(businessRetrievalPort, times(1)).existsActiveByName(eq(command.newName()));
        verify(businessPersistencePort, never()).save(any(Business.class));
        verify(businessNotificationPort, never()).notifyNewBusiness(any(Business.class));
        verify(businessLockPort, times(1)).lock(eq(command.newName()), eq(true));
        verify(businessLockPort, times(1)).lock(eq(command.businessId()), eq(true));
        verify(businessLockPort, times(1)).lock(eq(command.newName()), eq(false));
        verify(businessLockPort, times(1)).lock(eq(command.businessId()), eq(false));
    }

    @Test
    void givenNonExistingBusinessId_whenChangeName_thenBusinessNotFoundExceptionThrown() {
        // Given
        BusinessChangeNameCommand command = new BusinessChangeNameCommand(
                -1,
                "New Name"
        );

        doReturn(false)
                .when(businessRetrievalPort)
                .existsActiveByName(eq(command.newName()));
        doThrow(BusinessNotFoundException.class)
                .when(businessRetrievalPort)
                .findById(eq(command.businessId()), eq(false));

        // When & Then
        assertThatThrownBy(() -> businessManagement.changeName(command))
                .isInstanceOf(BusinessNotFoundException.class);
        verify(businessRetrievalPort, times(1)).findById(eq(command.businessId()), eq(false));
        verify(businessRetrievalPort, times(1)).existsActiveByName(eq(command.newName()));
        verify(businessPersistencePort, never()).save(any(Business.class));
        verify(businessNotificationPort, never()).notifyNewBusiness(any(Business.class));
        verify(businessLockPort, times(1)).lock(command.newName(), true);
        verify(businessLockPort, times(1)).lock(command.businessId(), true);
        verify(businessLockPort, times(1)).lock(command.newName(), false);
        verify(businessLockPort, times(1)).lock(command.businessId(), false);
    }

    @Test
    void givenValidBusinessChangeActiveCommand_whenChangeActive_thenActiveStatusChanged() {
        // Given
        BusinessChangeActiveCommand command = new BusinessChangeActiveCommand(1, true);
        Business business = mock(Business.class);
        String businessName = "Name";

        doReturn(businessName)
                .when(business)
                .getName();
        doReturn(business)
                .when(businessRetrievalPort)
                .findById(eq(command.businessId()), eq(false));

        // When
        businessManagement.changeActive(command);

        // Then
        verify(businessRetrievalPort, times(1)).findById(eq(command.businessId()), eq(false));
        verify(businessPersistencePort, times(1)).save(same(business));
        verify(businessNotificationPort, times(1)).notifyBusinessActiveChanged(same(business));
        verify(businessLockPort, times(1)).lock(eq(command.businessId()), eq(true));
        verify(businessLockPort, times(1)).lock(eq(businessName), eq(true));
        verify(businessLockPort, times(1)).lock(eq(businessName), eq(false));
        verify(businessLockPort, times(1)).lock(eq(command.businessId()), eq(false));
    }

    @Test
    void givenExistingActiveBusinessName_whenChangeActive_thenBusinessNameAlreadyTakenExceptionThrown() {
        // Given
        BusinessChangeActiveCommand command = new BusinessChangeActiveCommand(1, true);
        Business business = mock(Business.class);
        String businessName = "Name";

        doReturn(businessName)
                .when(business)
                .getName();
        doReturn(business)
                .when(businessRetrievalPort)
                .findById(eq(command.businessId()), eq(false));
        doReturn(true)
                .when(businessRetrievalPort)
                .existsActiveByName(eq(businessName));

        // When & Then
        assertThatThrownBy(() -> businessManagement.changeActive(command))
                .isInstanceOf(BusinessNameAlreadyTaken.class);
        verify(businessRetrievalPort, times(1)).findById(eq(command.businessId()), eq(false));
        verify(businessPersistencePort, never()).save(same(business));
        verify(businessNotificationPort, never()).notifyBusinessActiveChanged(same(business));
        verify(businessRetrievalPort, times(1)).existsActiveByName(eq(businessName));
        verify(businessLockPort, times(1)).lock(eq(command.businessId()), eq(true));
        verify(businessLockPort, times(1)).lock(eq(command.businessId()), eq(true));
        verify(businessLockPort, times(1)).lock(eq(businessName), eq(false));
        verify(businessLockPort, times(1)).lock(eq(businessName), eq(false));
    }

    @Test
    void givenNonExistingBusinessId_whenChangeActive_thenBusinessNotFoundExceptionThrown() {
        // Given
        BusinessChangeActiveCommand command = new BusinessChangeActiveCommand(
                -1,
                true
        );

        doThrow(BusinessNotFoundException.class)
                .when(businessRetrievalPort)
                .findById(eq(command.businessId()), eq(false));

        // When & Then
        assertThatThrownBy(() -> businessManagement.changeActive(command))
                .isInstanceOf(BusinessNotFoundException.class);
        verify(businessRetrievalPort, times(1)).findById(eq(command.businessId()), eq(false));
        verify(businessPersistencePort, never()).save(any(Business.class));
        verify(businessNotificationPort, never()).notifyBusinessActiveChanged(any(Business.class));
        verify(businessRetrievalPort, never()).existsActiveByName(any(String.class));
        verify(businessLockPort, times(1)).lock(eq(command.businessId()), eq(true));
        verify(businessLockPort, times(1)).lock(eq(command.businessId()), eq(false));
        verify(businessLockPort, never()).lock(any(String.class), eq(true));
    }

    @Test
    void givenValidBusinessChangeDetailsCommand_whenChangeDetails_thenDetailsChanged() {
        // Given
        BusinessChangeDetailsCommand command = new BusinessChangeDetailsCommand(
                1,
                "New Description"
        );
        Business business = mock(Business.class);

        doReturn(business).when(businessRetrievalPort).findById(eq(command.businessId()), eq(false));

        // When
        businessManagement.changeDetails(command);

        // Then
        verify(businessRetrievalPort, times(1)).findById(eq(command.businessId()), eq(false));
        verify(businessPersistencePort, times(1)).save(eq(business));
        verify(business, times(1)).setDescription(eq(command.description()));
    }

    @Test
    void givenNonExistingBusinessId_whenChangeDetails_thenBusinessNotFoundExceptionThrown() {
        // Given
        BusinessChangeDetailsCommand command = new BusinessChangeDetailsCommand(
                -1,
                "New Description"
        );

        doThrow(BusinessNotFoundException.class)
                .when(businessRetrievalPort)
                .findById(eq(command.businessId()), eq(false));

        // When & Then
        assertThatThrownBy(() -> businessManagement.changeDetails(command))
                .isInstanceOf(BusinessNotFoundException.class);
        verify(businessRetrievalPort, times(1)).findById(eq(command.businessId()), eq(false));
        verify(businessPersistencePort, never()).save(any(Business.class));
    }
}