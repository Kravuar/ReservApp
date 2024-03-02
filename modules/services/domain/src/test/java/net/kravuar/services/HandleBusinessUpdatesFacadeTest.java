package net.kravuar.services;

import net.kravuar.services.domain.Service;
import net.kravuar.services.domain.commands.HandleBusinessActiveChangeCommand;
import net.kravuar.services.ports.out.ServicePersistencePort;
import net.kravuar.services.ports.out.ServiceRetrievalPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HandleBusinessUpdatesFacadeTest {
    @Mock
    ServiceRetrievalPort serviceRetrievalPort;
    @Mock
    ServicePersistencePort servicePersistencePort;
    @InjectMocks
    HandleBusinessUpdatesFacade handleBusinessUpdates;

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void givenValidHandleBusinessActiveChangeCommand_whenOnActivityChange_thenAssociatedServicesUpdated(boolean active) {
        // Given
        HandleBusinessActiveChangeCommand command = new HandleBusinessActiveChangeCommand(
                1,
                active
        );
        List<Service> associatedServices = Arrays.asList(
                mock(Service.class),
                mock(Service.class),
                mock(Service.class)
        );

        doReturn(associatedServices)
                .when(serviceRetrievalPort)
                .findAllByBusinessId(eq(command.businessId()), eq(false));

        // When
        handleBusinessUpdates.onActivityChange(command);

        // Then
        verify(serviceRetrievalPort, times(1)).findAllByBusinessId(eq(command.businessId()), eq(false));
        verify(servicePersistencePort, times(associatedServices.size())).save(any(Service.class));
        for (Service service : associatedServices)
            verify(service, times(1)).setActive(eq(command.active()));
    }

    @Test
    void givenNoAssociatedServices_whenOnActivityChange_thenNoServicesUpdated() {
        // Given
        HandleBusinessActiveChangeCommand command = new HandleBusinessActiveChangeCommand(
                1,
                true
        );

        doReturn(Collections.emptyList())
                .when(serviceRetrievalPort)
                .findAllByBusinessId(eq(command.businessId()), eq(false));

        // When
        handleBusinessUpdates.onActivityChange(command);

        // Then
        verify(serviceRetrievalPort, times(1)).findAllByBusinessId(eq(command.businessId()), eq(false));
        verifyNoInteractions(servicePersistencePort);
    }
}