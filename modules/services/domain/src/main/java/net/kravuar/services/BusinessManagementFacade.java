package net.kravuar.services;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.services.domain.Business;
import net.kravuar.services.domain.Service;
import net.kravuar.services.domain.commands.BusinessChangeActiveCommand;
import net.kravuar.services.domain.commands.BusinessCreationCommand;
import net.kravuar.services.ports.in.BusinessManagementUseCase;
import net.kravuar.services.ports.out.BusinessPersistencePort;
import net.kravuar.services.ports.out.BusinessRetrievalPort;
import net.kravuar.services.ports.out.ServicePersistencePort;
import net.kravuar.services.ports.out.ServiceRetrievalPort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AppComponent
@RequiredArgsConstructor
public class BusinessManagementFacade implements BusinessManagementUseCase {
    private final ServiceRetrievalPort serviceRetrievalPort;
    private final ServicePersistencePort servicePersistencePort;
    private final BusinessRetrievalPort businessRetrievalPort;
    private final BusinessPersistencePort businessPersistencePort;

    @Override
    public Business create(BusinessCreationCommand command) {
        return businessPersistencePort.save(Business.builder()
                .ownerSub(command.ownerSub())
                .active(command.active())
                .build()
        );
    }

    // Seems kind of error-prone in terms of inter-service consistency
    @Override
    @Transactional
    public void changeActive(BusinessChangeActiveCommand command) {
        Business existing = businessRetrievalPort.findById(command.businessId());
        existing.setActive(command.active());
        List<Service> associated = serviceRetrievalPort.findAllByBusiness(command.businessId());
        associated.forEach(service -> {
            service.setActive(command.active());
            servicePersistencePort.save(service); // It's okay to use save and not saveAll, as we already have transaction for whole method
        });
        businessPersistencePort.save(existing);
    }
}
