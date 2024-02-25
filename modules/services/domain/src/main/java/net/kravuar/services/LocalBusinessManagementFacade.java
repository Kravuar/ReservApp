package net.kravuar.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.services.domain.Business;
import net.kravuar.services.domain.Service;
import net.kravuar.services.domain.commands.BusinessChangeActiveCommand;
import net.kravuar.services.domain.commands.BusinessCreationCommand;
import net.kravuar.services.ports.in.LocalBusinessManagementUseCase;
import net.kravuar.services.ports.out.*;

import java.util.List;

@AppComponent
@RequiredArgsConstructor
public class LocalBusinessManagementFacade implements LocalBusinessManagementUseCase {
    private final ServiceRetrievalPort serviceRetrievalPort;
    private final ServicePersistencePort servicePersistencePort;
    private final BusinessRetrievalPort businessRetrievalPort;
    private final BusinessPersistencePort businessPersistencePort;
    private final BusinessLockPort businessLockPort;
    private final ServiceLockPort serviceLockPort;

    @Override
    public Business create(BusinessCreationCommand command) {
        return businessPersistencePort.save(Business.builder()
                .id(command.businessId())
                .ownerSub(command.ownerSub())
                .active(command.active())
                .build()
        );
    }

    // Seems kind of error-prone in terms of inter-service consistency
    @Override
    @Transactional
    public void changeActive(BusinessChangeActiveCommand command) {
        try {
            businessLockPort.lock(command.businessId(), true);

            Business existing = businessRetrievalPort.findById(command.businessId());
            existing.setActive(command.active());

            List<Service> associated = serviceRetrievalPort.findAllByBusiness(command.businessId());
            try {
                associated.forEach(service -> {
                    serviceLockPort.lock(service.getId(), true);
                    service.setActive(command.active());
                    servicePersistencePort.save(service);
                });

                businessPersistencePort.save(existing);
            } finally {
                associated.forEach(service -> serviceLockPort.lock(service.getId(), false));
            }
        } finally {
            businessLockPort.lock(command.businessId(), false);
        }
    }
}
