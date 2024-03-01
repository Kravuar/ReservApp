package net.kravuar.services;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.services.domain.Business;
import net.kravuar.services.domain.Service;
import net.kravuar.services.domain.commands.ServiceChangeActiveCommand;
import net.kravuar.services.domain.commands.ServiceChangeDetailsCommand;
import net.kravuar.services.domain.commands.ServiceChangeNameCommand;
import net.kravuar.services.domain.commands.ServiceCreationCommand;
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

            if (serviceRetrievalPort.existsActiveByName(command.name()))
                throw new ServiceNameAlreadyTaken();
            Business business = businessRetrievalPort.findById(command.businessId(), true);
            Service existing = servicePersistencePort.save(
                    new Service(
                            null,
                            business,
                            command.name(),
                            true,
                            command.description()
                    )
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

            if (serviceRetrievalPort.existsActiveByName(command.newName()))
                throw new ServiceNameAlreadyTaken();
            Service existing = serviceRetrievalPort.findById(command.serviceId(), false);
            existing.setName(command.newName());
            servicePersistencePort.save(existing);
        } finally {
            serviceLockPort.lock(command.newName(), false);
            serviceLockPort.lock(command.serviceId(), false);
        }
    }

    @Override
    public void changeActive(ServiceChangeActiveCommand command) {
        try {
            serviceLockPort.lock(command.serviceId(), true);

            Service service = serviceRetrievalPort.findById(command.serviceId(), false);

            try {
                if (command.active())
                    serviceLockPort.lock(service.getName(), true);

                if (serviceRetrievalPort.existsActiveByName(service.getName()))
                    throw new ServiceNameAlreadyTaken();

                service.setActive(command.active());
                service = servicePersistencePort.save(service);
                serviceNotificationPort.notifyServiceActiveChanged(service);
            } finally {
                serviceLockPort.lock(service.getName(), false);
            }
        } finally {
            serviceLockPort.lock(command.serviceId(), false);
        }
    }

    @Override
    public void changeDetails(ServiceChangeDetailsCommand command) {
        Service service = serviceRetrievalPort.findById(command.serviceId(), false);
        service.setDescription(command.description());
        servicePersistencePort.save(service);
    }
}
