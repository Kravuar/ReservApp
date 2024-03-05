package net.kravuar.services;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.services.domain.Business;
import net.kravuar.services.domain.Service;
import net.kravuar.services.domain.commands.ServiceChangeActiveCommand;
import net.kravuar.services.domain.commands.ServiceChangeDetailsCommand;
import net.kravuar.services.domain.commands.ServiceCreationCommand;
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
        Business business = businessRetrievalPort.findActiveById(command.businessId());
        Service existing = servicePersistencePort.save(
                new Service(
                        null,
                        business,
                        true,
                        command.name(),
                        command.description()
                )
        );
        serviceNotificationPort.notifyNewService(existing);
        return existing;
    }

    @Override
    public void changeActive(ServiceChangeActiveCommand command) {
        try {
            serviceLockPort.lock(command.serviceId(), true);

            Service service = serviceRetrievalPort.findById(command.serviceId(), false);
            service.setActive(command.active());
            service = servicePersistencePort.save(service);
            serviceNotificationPort.notifyServiceActiveChanged(service);
        } finally {
            serviceLockPort.lock(command.serviceId(), false);
        }
    }

    @Override
    public void changeDetails(ServiceChangeDetailsCommand command) {
        try {
            serviceLockPort.lock(command.serviceId(), true);

            Service service = serviceRetrievalPort.findById(command.serviceId(), false);
            if (command.name() != null)
                service.setName(command.name());
            if (command.description() != null)
                service.setDescription(command.description());

            servicePersistencePort.save(service);
        } finally {
            serviceLockPort.lock(command.serviceId(), false);
        }
    }
}
