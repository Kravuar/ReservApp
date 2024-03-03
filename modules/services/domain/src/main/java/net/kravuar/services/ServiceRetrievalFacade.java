package net.kravuar.services;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.services.domain.Service;
import net.kravuar.services.ports.in.ServiceRetrievalUseCase;
import net.kravuar.services.ports.out.ServiceRetrievalPort;

import java.util.List;

@AppComponent
@RequiredArgsConstructor
public class ServiceRetrievalFacade implements ServiceRetrievalUseCase {
    private final ServiceRetrievalPort serviceRetrievalPort;

    @Override
    public Service findById(long serviceId, boolean activeOnly) {
        return serviceRetrievalPort.findById(serviceId, activeOnly);
    }

    @Override
    public List<Service> findAllByActiveBusinessId(long businessId, boolean activeOnly) {
        return serviceRetrievalPort.findAllByActiveBusinessId(businessId, activeOnly);
    }

    @Override
    public List<Service> findAll() {
        return serviceRetrievalPort.findAllActive();
    }
}
