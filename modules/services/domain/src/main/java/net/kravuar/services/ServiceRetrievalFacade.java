package net.kravuar.services;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.services.domain.Business;
import net.kravuar.services.domain.Service;
import net.kravuar.services.ports.in.ServiceRetrievalUseCase;
import net.kravuar.services.ports.out.BusinessRetrievalPort;
import net.kravuar.services.ports.out.ServiceRetrievalPort;

import java.util.List;

@AppComponent
@RequiredArgsConstructor
public class ServiceRetrievalFacade implements ServiceRetrievalUseCase {
    private final ServiceRetrievalPort serviceRetrievalPort;
    private final BusinessRetrievalPort businessRetrievalPort;

    @Override
    public Service findById(long id) {
        return serviceRetrievalPort.findById(id);
    }

    @Override
    public List<Service> findAllActiveByBusiness(long businessId) {
        Business business = businessRetrievalPort.findById(businessId);
        return serviceRetrievalPort.findAllActiveByBusiness(business);
    }

    @Override
    public List<Service> findAllActive() {
        return serviceRetrievalPort.findAllActive();
    }
}
