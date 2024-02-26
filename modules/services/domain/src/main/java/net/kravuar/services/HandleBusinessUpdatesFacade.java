package net.kravuar.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.services.domain.Business;
import net.kravuar.services.domain.Service;
import net.kravuar.services.domain.commands.HandleBusinessActiveChangeCommand;
import net.kravuar.services.ports.in.HandleBusinessUpdatesUseCase;
import net.kravuar.services.ports.out.BusinessRetrievalPort;
import net.kravuar.services.ports.out.ServiceLockPort;
import net.kravuar.services.ports.out.ServicePersistencePort;
import net.kravuar.services.ports.out.ServiceRetrievalPort;

import java.util.List;

@AppComponent
@RequiredArgsConstructor
public class HandleBusinessUpdatesFacade implements HandleBusinessUpdatesUseCase {
    private final BusinessRetrievalPort businessRetrievalPort;
    private final ServiceRetrievalPort serviceRetrievalPort;
    private final ServicePersistencePort servicePersistencePort;
    private final ServiceLockPort serviceLockPort;

    @Override
    @Transactional
    public void onActivityChange(HandleBusinessActiveChangeCommand command) {
        Business business = businessRetrievalPort.findById(command.businessId());

        List<Service> associated = serviceRetrievalPort.findAllByBusiness(business);
        try {
            associated.forEach(service -> {
                serviceLockPort.lock(service.getId(), true);
                service.setActive(command.active());
                servicePersistencePort.save(service);
            });

        } finally {
            associated.forEach(service -> serviceLockPort.lock(service.getId(), false));
        }
    }
}
