package net.kravuar.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.services.domain.Service;
import net.kravuar.services.domain.commands.HandleBusinessActiveChangeCommand;
import net.kravuar.services.ports.in.HandleBusinessUpdatesUseCase;
import net.kravuar.services.ports.out.ServicePersistencePort;
import net.kravuar.services.ports.out.ServiceRetrievalPort;

import java.util.List;

@AppComponent
@RequiredArgsConstructor
public class HandleBusinessUpdatesFacade implements HandleBusinessUpdatesUseCase {
    private final ServiceRetrievalPort serviceRetrievalPort;
    private final ServicePersistencePort servicePersistencePort;

    @Override
    @Transactional
    public void onActivityChange(HandleBusinessActiveChangeCommand command) {
        List<Service> associated = serviceRetrievalPort.findAllByBusinessId(command.businessId(), false);
        associated.forEach(service -> {
            service.setActive(command.active());
            servicePersistencePort.save(service);
        });
    }
}
