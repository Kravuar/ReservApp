package net.kravuar.services;

import net.kravuar.services.domain.Business;
import net.kravuar.services.domain.Service;
import net.kravuar.services.domain.commands.ServiceChangeActiveCommand;
import net.kravuar.services.domain.commands.ServiceChangeDetailsCommand;
import net.kravuar.services.domain.commands.ServiceChangeNameCommand;
import net.kravuar.services.domain.commands.ServiceCreationCommand;
import net.kravuar.services.domain.exceptions.ServiceNameAlreadyTaken;
import net.kravuar.services.domain.exceptions.ServiceNotFoundException;
import net.kravuar.services.ports.out.*;
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
class ServiceManagementFacadeTest {
    @Mock
    ServicePersistencePort servicePersistencePort;
    @Mock
    ServiceRetrievalPort serviceRetrievalPort;
    @Mock
    BusinessRetrievalPort businessRetrievalPort;
    @Mock
    ServiceNotificationPort serviceNotificationPort;
    @Mock
    ServiceLockPort serviceLockPort;
    @InjectMocks
    ServiceManagementFacade serviceManagement;


    @Test
    void givenValidServiceCreationCommand_whenCreate_thenNewServiceCreated() {
        // Given
        ServiceCreationCommand command = new ServiceCreationCommand(
                1,
                "New Service",
                "Description"
        );

        doReturn(mock(Business.class))
                .when(businessRetrievalPort)
                .findById(anyLong(), anyBoolean());
        doReturn(false)
                .when(serviceRetrievalPort)
                .existsActiveByName(eq(command.name()));
        doReturn(mock(Service.class))
                .when(servicePersistencePort)
                .save(any(Service.class));

        // When
        Service newService = serviceManagement.create(command);

        // Then
        verify(serviceLockPort, times(1)).lock(eq(command.name()), eq(true));
        verify(serviceLockPort, times(1)).lock(eq(command.name()), eq(false));
        verify(serviceRetrievalPort, times(1)).existsActiveByName(eq(command.name()));
        verify(servicePersistencePort, times(1)).save(any(Service.class));
        verify(serviceNotificationPort, times(1)).notifyNewService(any(Service.class));
        assertThat(newService).isNotNull();
    }

    @Test
    void givenExistingServiceName_whenCreate_thenServiceNameAlreadyTakenExceptionThrown() {
        // Given
        ServiceCreationCommand command = new ServiceCreationCommand(
                1,
                "Existing Service",
                "Description"
        );

        doReturn(true)
                .when(serviceRetrievalPort)
                .existsActiveByName(eq(command.name()));

        // When & Then
        assertThatThrownBy(() -> serviceManagement.create(command))
                .isInstanceOf(ServiceNameAlreadyTaken.class);
        verify(serviceRetrievalPort, times(1)).existsActiveByName(eq(command.name()));
        verify(servicePersistencePort, never()).save(any(Service.class));
        verify(serviceNotificationPort, never()).notifyNewService(any(Service.class));
        verify(serviceLockPort, times(1)).lock(eq(command.name()), eq(true));
        verify(serviceLockPort, times(1)).lock(eq(command.name()), eq(false));
    }

    @Test
    void givenValidServiceChangeNameCommand_whenChangeName_thenNameChanged() {
        // Given
        ServiceChangeNameCommand command = new ServiceChangeNameCommand(
                1,
                "New Name"
        );
        Service service = mock(Service.class);

        doReturn(service)
                .when(serviceRetrievalPort)
                .findById(eq(command.serviceId()), eq(false));
        doReturn(false)
                .when(serviceRetrievalPort)
                .existsActiveByName(eq(command.newName()));

        // When
        serviceManagement.changeName(command);

        // Then
        verify(serviceLockPort, times(1)).lock(eq(command.serviceId()), eq(true));
        verify(serviceLockPort, times(1)).lock(eq(command.newName()), eq(true));
        verify(serviceRetrievalPort, times(1)).findById(eq(command.serviceId()), eq(false));
        verify(serviceRetrievalPort, times(1)).existsActiveByName(eq(command.newName()));
        verify(servicePersistencePort, times(1)).save(eq(service));
        verify(serviceLockPort, times(1)).lock(eq(command.newName()), eq(false));
        verify(serviceLockPort, times(1)).lock(eq(command.serviceId()), eq(false));
    }

    @Test
    void givenExistingServiceName_whenChangeName_thenServiceNameAlreadyTakenExceptionThrown() {
        // Given
        ServiceChangeNameCommand command = new ServiceChangeNameCommand(
                1,
                "Existing Name"
        );

        doReturn(true)
                .when(serviceRetrievalPort)
                .existsActiveByName(eq(command.newName()));

        // When & Then
        assertThatThrownBy(() -> serviceManagement.changeName(command))
                .isInstanceOf(ServiceNameAlreadyTaken.class);
        verify(serviceRetrievalPort, times(1)).existsActiveByName(eq(command.newName()));
        verify(servicePersistencePort, never()).save(any(Service.class));
        verify(serviceNotificationPort, never()).notifyNewService(any(Service.class));
        verify(serviceLockPort, times(1)).lock(eq(command.newName()), eq(true));
        verify(serviceLockPort, times(1)).lock(eq(command.serviceId()), eq(true));
        verify(serviceLockPort, times(1)).lock(eq(command.newName()), eq(false));
        verify(serviceLockPort, times(1)).lock(eq(command.serviceId()), eq(false));
    }

    @Test
    void givenNonExistingServiceId_whenChangeName_thenServiceNotFoundExceptionThrown() {
        // Given
        ServiceChangeNameCommand command = new ServiceChangeNameCommand(
                -1,
                "New Name"
        );

        doReturn(false)
                .when(serviceRetrievalPort)
                .existsActiveByName(eq(command.newName()));
        doThrow(ServiceNotFoundException.class)
                .when(serviceRetrievalPort)
                .findById(eq(command.serviceId()), eq(false));

        // When & Then
        assertThatThrownBy(() -> serviceManagement.changeName(command))
                .isInstanceOf(ServiceNotFoundException.class);
        verify(serviceRetrievalPort, times(1)).findById(eq(command.serviceId()), eq(false));
        verify(serviceRetrievalPort, times(1)).existsActiveByName(eq(command.newName()));
        verify(servicePersistencePort, never()).save(any(Service.class));
        verify(serviceNotificationPort, never()).notifyNewService(any(Service.class));
        verify(serviceLockPort, times(1)).lock(command.newName(), true);
        verify(serviceLockPort, times(1)).lock(command.serviceId(), true);
        verify(serviceLockPort, times(1)).lock(command.newName(), false);
        verify(serviceLockPort, times(1)).lock(command.serviceId(), false);
    }

    @Test
    void givenValidServiceChangeActiveCommand_whenChangeActive_thenActiveStatusChanged() {
        // Given
        ServiceChangeActiveCommand command = new ServiceChangeActiveCommand(1, true);
        Service service = mock(Service.class);
        String serviceName = "Name";

        doReturn(serviceName)
                .when(service)
                .getName();
        doReturn(service)
                .when(serviceRetrievalPort)
                .findById(eq(command.serviceId()), eq(false));
        doReturn(service)
                .when(servicePersistencePort)
                .save(same(service));

        // When
        serviceManagement.changeActive(command);

        // Then
        verify(serviceRetrievalPort, times(1)).findById(eq(command.serviceId()), eq(false));
        verify(servicePersistencePort, times(1)).save(same(service));
        verify(serviceNotificationPort, times(1)).notifyServiceActiveChanged(same(service));
        verify(serviceLockPort, times(1)).lock(eq(command.serviceId()), eq(true));
        verify(serviceLockPort, times(1)).lock(eq(serviceName), eq(true));
        verify(serviceLockPort, times(1)).lock(eq(serviceName), eq(false));
        verify(serviceLockPort, times(1)).lock(eq(command.serviceId()), eq(false));
    }

    @Test
    void givenExistingActiveServiceName_whenChangeActive_thenServiceNameAlreadyTakenExceptionThrown() {
        // Given
        ServiceChangeActiveCommand command = new ServiceChangeActiveCommand(1, true);
        Service service = mock(Service.class);
        String serviceName = "Name";

        doReturn(serviceName)
                .when(service)
                .getName();
        doReturn(service)
                .when(serviceRetrievalPort)
                .findById(eq(command.serviceId()), eq(false));
        doReturn(true)
                .when(serviceRetrievalPort)
                .existsActiveByName(eq(serviceName));

        // When & Then
        assertThatThrownBy(() -> serviceManagement.changeActive(command))
                .isInstanceOf(ServiceNameAlreadyTaken.class);
        verify(serviceRetrievalPort, times(1)).findById(eq(command.serviceId()), eq(false));
        verify(servicePersistencePort, never()).save(same(service));
        verify(serviceNotificationPort, never()).notifyServiceActiveChanged(same(service));
        verify(serviceRetrievalPort, times(1)).existsActiveByName(eq(serviceName));
        verify(serviceLockPort, times(1)).lock(eq(command.serviceId()), eq(true));
        verify(serviceLockPort, times(1)).lock(eq(command.serviceId()), eq(true));
        verify(serviceLockPort, times(1)).lock(eq(serviceName), eq(false));
        verify(serviceLockPort, times(1)).lock(eq(serviceName), eq(false));
    }

    @Test
    void givenNonExistingServiceId_whenChangeActive_thenServiceNotFoundExceptionThrown() {
        // Given
        ServiceChangeActiveCommand command = new ServiceChangeActiveCommand(
                -1,
                true
        );

        doThrow(ServiceNotFoundException.class)
                .when(serviceRetrievalPort)
                .findById(eq(command.serviceId()), eq(false));

        // When & Then
        assertThatThrownBy(() -> serviceManagement.changeActive(command))
                .isInstanceOf(ServiceNotFoundException.class);
        verify(serviceRetrievalPort, times(1)).findById(eq(command.serviceId()), eq(false));
        verify(servicePersistencePort, never()).save(any(Service.class));
        verify(serviceNotificationPort, never()).notifyServiceActiveChanged(any(Service.class));
        verify(serviceRetrievalPort, never()).existsActiveByName(any(String.class));
        verify(serviceLockPort, times(1)).lock(eq(command.serviceId()), eq(true));
        verify(serviceLockPort, times(1)).lock(eq(command.serviceId()), eq(false));
        verify(serviceLockPort, never()).lock(any(String.class), eq(true));
    }

    @Test
    void givenValidServiceChangeDetailsCommand_whenChangeDetails_thenDetailsChanged() {
        // Given
        ServiceChangeDetailsCommand command = new ServiceChangeDetailsCommand(
                1,
                "New Description"
        );
        Service service = mock(Service.class);

        doReturn(service).when(serviceRetrievalPort).findById(eq(command.serviceId()), eq(false));

        // When
        serviceManagement.changeDetails(command);

        // Then
        verify(serviceRetrievalPort, times(1)).findById(eq(command.serviceId()), eq(false));
        verify(servicePersistencePort, times(1)).save(eq(service));
        verify(service, times(1)).setDescription(eq(command.description()));
    }

    @Test
    void givenNonExistingServiceId_whenChangeDetails_thenServiceNotFoundExceptionThrown() {
        // Given
        ServiceChangeDetailsCommand command = new ServiceChangeDetailsCommand(
                -1,
                "New Description"
        );

        doThrow(ServiceNotFoundException.class)
                .when(serviceRetrievalPort)
                .findById(eq(command.serviceId()), eq(false));

        // When & Then
        assertThatThrownBy(() -> serviceManagement.changeDetails(command))
                .isInstanceOf(ServiceNotFoundException.class);
        verify(serviceRetrievalPort, times(1)).findById(eq(command.serviceId()), eq(false));
        verify(servicePersistencePort, never()).save(any(Service.class));
    }
}