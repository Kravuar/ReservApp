package net.kravuar.services;

import net.kravuar.services.domain.Business;
import net.kravuar.services.domain.Service;
import net.kravuar.services.domain.commands.ServiceChangeActiveCommand;
import net.kravuar.services.domain.commands.ServiceChangeDetailsCommand;
import net.kravuar.services.domain.commands.ServiceCreationCommand;
import net.kravuar.services.domain.exceptions.ServiceNotFoundException;
import net.kravuar.services.ports.out.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
        Service service = new Service();

        doReturn(mock(Business.class))
                .when(businessRetrievalPort)
                .findActiveById(anyLong());
        doReturn(service)
                .when(servicePersistencePort)
                .save(any(Service.class));

        // When
        Service newService = serviceManagement.create(command);

        // Then
        verify(servicePersistencePort).save(same(service));
        verify(serviceNotificationPort).notifyNewService(same(service));
        assertThat(newService).isSameAs(service);
    }

    @Test
    void givenValidServiceChangeActiveCommand_whenChangeActive_thenActiveStatusChanged() {
        // Given
        ServiceChangeActiveCommand command = new ServiceChangeActiveCommand(1, true);
        Service service = spy(new Service());

        doReturn(service)
                .when(serviceRetrievalPort)
                .findById(eq(command.serviceId()), eq(false));
        doReturn(service)
                .when(servicePersistencePort)
                .save(same(service));

        // When
        serviceManagement.changeActive(command);

        // Then
        verify(servicePersistencePort).save(same(service));
        verify(serviceNotificationPort).notifyServiceActiveChanged(same(service));
        verify(serviceLockPort).lock(eq(command.serviceId()), eq(false));
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
        verify(servicePersistencePort, never()).save(any(Service.class));
        verify(serviceNotificationPort, never()).notifyServiceActiveChanged(any(Service.class));
        verify(serviceLockPort).lock(eq(command.serviceId()), eq(false));
    }

    @ParameterizedTest
    @CsvSource({
            "New Name, New Description",
            ", New Description",
            "New Name,",
            ","
    })
    void givenValidServiceChangeDetailsCommand_whenChangeDetails_thenOnlySpecifiedDetailsChanged(String name, String description) {
        // Given
        ServiceChangeDetailsCommand command = new ServiceChangeDetailsCommand(
                1,
                name,
                description
        );
        Service service = spy(new Service());

        doReturn(service)
                .when(serviceRetrievalPort)
                .findById(eq(command.serviceId()), eq(false));

        // When
        serviceManagement.changeDetails(command);

        // Then
        verify(servicePersistencePort).save(eq(service));
        if (command.name() != null)
            verify(service).setName(eq(command.name()));
        else
            verify(service, times(0)).setName(anyString());
        if (command.description() != null)
            verify(service).setDescription(eq(command.description()));
        else
            verify(service, times(0)).setDescription(anyString());
    }

    @Test
    void givenNonExistingServiceId_whenChangeDetails_thenServiceNotFoundExceptionThrown() {
        // Given
        ServiceChangeDetailsCommand command = new ServiceChangeDetailsCommand(
                -1,
                "New Name",
                "New Description"
        );

        doThrow(ServiceNotFoundException.class)
                .when(serviceRetrievalPort)
                .findById(eq(command.serviceId()), eq(false));

        // When & Then
        assertThatThrownBy(() -> serviceManagement.changeDetails(command))
                .isInstanceOf(ServiceNotFoundException.class);
        verify(servicePersistencePort, never()).save(any(Service.class));
    }
}