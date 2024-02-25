package net.kravuar.staff;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.staff.domain.Business;
import net.kravuar.staff.domain.Service;
import net.kravuar.staff.domain.commands.ServiceChangeActiveCommand;
import net.kravuar.staff.domain.commands.ServiceCreationCommand;
import net.kravuar.staff.ports.in.LocalServiceManagementUseCase;
import net.kravuar.staff.ports.out.BusinessRetrievalPort;
import net.kravuar.staff.ports.out.ServiceLockPort;
import net.kravuar.staff.ports.out.ServicePersistencePort;
import net.kravuar.staff.ports.out.ServiceRetrievalPort;

@AppComponent
@RequiredArgsConstructor
public class LocalServiceManagementFacade implements LocalServiceManagementUseCase {
    private final BusinessRetrievalPort businessRetrievalPort;
    private final ServiceRetrievalPort serviceRetrievalPort;
    private final ServicePersistencePort servicePersistencePort;
    private final ServiceLockPort serviceLockPort;

    @Override
    public Service create(ServiceCreationCommand command) {
        Business business = businessRetrievalPort.findById(command.businessId());

        return servicePersistencePort.save(Service.builder()
                .id(command.businessId())
                .business(business)
                .active(command.active())
                .build()
        );
    }

    @Override
    public void changeActive(ServiceChangeActiveCommand command) {
        try {
            serviceLockPort.lock(command.serviceId(), true);

            Service existing = serviceRetrievalPort.findById(command.serviceId());
            existing.setActive(command.active());
            servicePersistencePort.save(existing);
        } finally {
            serviceLockPort.lock(command.serviceId(), false);
        }
    }
}
