package net.kravuar.schedule;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.schedule.model.Service;
import net.kravuar.schedule.ports.in.ServiceRetrievalUseCase;
import net.kravuar.schedule.ports.out.ServiceRetrievalPort;

@AppComponent
@RequiredArgsConstructor
public class ServiceRetrievalFacade implements ServiceRetrievalUseCase {
    private final ServiceRetrievalPort serviceRetrievalPort;

    @Override
    public Service findActiveById(long serviceId) {
        return serviceRetrievalPort.findActiveById(serviceId);
    }
}
