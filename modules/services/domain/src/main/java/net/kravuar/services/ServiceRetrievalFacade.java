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
    public Service findById(long id) {
        return serviceRetrievalPort.findById(id);
    }

    @Override
    public List<Service> findAllActiveByBusiness(long businessId) {
        return serviceRetrievalPort.findAllActiveByBusiness(businessId);
    }

    @Override
    public List<Service> findAllActive() {
        return serviceRetrievalPort.findAllActive();
    }
}
