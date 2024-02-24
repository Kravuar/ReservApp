package net.kravuar.services;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.services.domain.Business;
import net.kravuar.services.domain.Service;
import net.kravuar.services.domain.commands.ServiceChangeActiveCommand;
import net.kravuar.services.domain.commands.ServiceChangeNameCommand;
import net.kravuar.services.domain.commands.ServiceCreationCommand;
import net.kravuar.services.domain.exceptions.BusinessDisabledException;
import net.kravuar.services.domain.exceptions.ServiceNameAlreadyTaken;
import net.kravuar.services.ports.in.ServiceManagementUseCase;
import net.kravuar.services.ports.out.BusinessRetrievalPort;
import net.kravuar.services.ports.out.ServiceNotificationPort;
import net.kravuar.services.ports.out.ServicePersistencePort;
import net.kravuar.services.ports.out.ServiceRetrievalPort;

@AppComponent
@RequiredArgsConstructor
public class ServiceManagementFacade implements ServiceManagementUseCase {
    private final ServicePersistencePort servicePersistencePort;
    private final ServiceRetrievalPort serviceRetrievalPort;
    private final ServiceNotificationPort serviceNotificationPort;
    private final BusinessRetrievalPort businessRetrievalPort;

    @Override
    public synchronized Service create(ServiceCreationCommand command) {
        Business business = businessRetrievalPort.findById(command.businessId());
        if (!business.isActive())
            throw new BusinessDisabledException();
        if (serviceRetrievalPort.existsByName(command.name()))
            throw new ServiceNameAlreadyTaken();
        Service existing = servicePersistencePort.save(
                Service.builder()
                        .name(command.name())
                        .business(business)
                        .active(true)
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
