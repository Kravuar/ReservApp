package net.kravuar.services;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.services.domain.Service;
import net.kravuar.services.domain.commands.ServiceChangeActiveCommand;
import net.kravuar.services.domain.commands.ServiceChangeNameCommand;
import net.kravuar.services.domain.commands.ServiceCreationCommand;
import net.kravuar.services.ports.in.ServiceManagementUseCase;
import net.kravuar.services.ports.out.ServiceNotificationPort;
import net.kravuar.services.ports.out.ServicePersistencePort;
import net.kravuar.services.ports.out.ServiceRetrievalPort;

@AppComponent
@RequiredArgsConstructor
public class ServiceManagementFacade implements ServiceManagementUseCase {
    private final ServicePersistencePort servicePersistencePort;
    private final ServiceRetrievalPort serviceRetrievalPort;
    private final ServiceNotificationPort serviceNotificationPort;

    @Override
    public Service create(ServiceCreationCommand command) {
        Service existing = servicePersistencePort.save(
                Service.builder()
                        .name(command.name())
                        .build()
        );
        serviceNotificationPort.notifyNewService(existing);
        return existing;
    }

    @Override
    public void changeName(ServiceChangeNameCommand command) {
        Service existing = serviceRetrievalPort.findById(command.serviceId());
        existing.setName(command.newName());
        servicePersistencePort.save(existing);
    }

    @Override
    public void changeActive(ServiceChangeActiveCommand command) {
        Service existing = serviceRetrievalPort.findById(command.serviceId());
        existing.setActive(command.active());
        existing = servicePersistencePort.save(existing);
        serviceNotificationPort.notifyServiceActiveChanged(existing);
    }
}
