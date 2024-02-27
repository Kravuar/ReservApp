package net.kravuar.services;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.services.domain.Business;
import net.kravuar.services.domain.Service;
import net.kravuar.services.domain.commands.ServiceChangeActiveCommand;
import net.kravuar.services.domain.commands.ServiceChangeDetailsCommand;
import net.kravuar.services.domain.commands.ServiceChangeNameCommand;
import net.kravuar.services.domain.commands.ServiceCreationCommand;
import net.kravuar.services.domain.exceptions.BusinessDisabledException;
import net.kravuar.services.domain.exceptions.ServiceNameAlreadyTaken;
import net.kravuar.services.ports.in.ServiceManagementUseCase;
import net.kravuar.services.ports.out.*;

@AppComponent
@RequiredArgsConstructor
public class ServiceManagementFacade implements ServiceManagementUseCase {
    private final ServicePersistencePort servicePersistencePort;
    private final ServiceRetrievalPort serviceRetrievalPort;
    private final ServiceNotificationPort serviceNotificationPort;
    private final ServiceLockPort serviceLockPort;
    private final BusinessRetrievalPort businessRetrievalPort;

    @Override
    public Service create(ServiceCreationCommand command) {
        try {
            serviceLockPort.lock(command.name(), true);

            if (serviceRetrievalPort.existsByName(command.name()))
                throw new ServiceNameAlreadyTaken();
            Business business = businessRetrievalPort.findById(command.businessId());
            if (!business.isActive())
                throw new BusinessDisabledException();
            Service existing = servicePersistencePort.save(
                    Service.builder()
                            .name(command.name())
                            .business(business)
                            .description(command.description())
                            .build()
            );
            serviceNotificationPort.notifyNewService(existing);
            return existing;
        } finally {
            serviceLockPort.lock(command.name(), false);
        }
    }

    @Override
    public void changeName(ServiceChangeNameCommand command) {
        try {
            serviceLockPort.lock(command.serviceId(), true);
            serviceLockPort.lock(command.newName(), true);

            if (serviceRetrievalPort.existsByName(command.newName()))
                throw new ServiceNameAlreadyTaken();
            Service existing = serviceRetrievalPort.findById(command.serviceId());
            existing.setName(command.newName());
            servicePersistencePort.save(existing);
        } finally {
            serviceLockPort.lock(command.newName(), false);
            serviceLockPort.lock(command.serviceId(), false);
        }
    }

    @Override
    public void changeActive(ServiceChangeActiveCommand command) {
        Service existing = serviceRetrievalPort.findById(command.serviceId());
        existing.setActive(command.active());
        existing = servicePersistencePort.save(existing);
        serviceNotificationPort.notifyServiceActiveChanged(existing);
    }

    @Override
    public void changeDetails(ServiceChangeDetailsCommand command) {
        Service service = serviceRetrievalPort.findById(command.serviceId());
        service.setDescription(command.description());
        servicePersistencePort.save(service);
    }
}
