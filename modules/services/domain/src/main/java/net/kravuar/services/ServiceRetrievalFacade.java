package net.kravuar.services;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.pageable.Page;
import net.kravuar.services.domain.exceptions.ServiceNotFoundException;
import net.kravuar.services.model.Service;
import net.kravuar.services.ports.in.ServiceRetrievalUseCase;
import net.kravuar.services.ports.out.ServiceRetrievalPort;

@AppComponent
@RequiredArgsConstructor
public class ServiceRetrievalFacade implements ServiceRetrievalUseCase {
    private final ServiceRetrievalPort serviceRetrievalPort;

    @Override
    public Service findById(long serviceId, boolean activeOnly) {
        return serviceRetrievalPort.findById(serviceId, activeOnly);
    }

    @Override
    public Page<Service> findActiveByActiveBusinessId(long businessId, boolean activeOnly, int page, int pageSize) {
        return serviceRetrievalPort.findActiveByActiveBusinessId(businessId, activeOnly, page, pageSize);
    }

    @Override
    public Page<Service> findActive(int page, int pageSize) {
        return serviceRetrievalPort.findActive(page, pageSize);
    }

    @Override
    public Service findByIdAndSub(long serviceId, String sub) {
        Service service = serviceRetrievalPort.findById(serviceId, false);
        if (service.getBusiness().getOwnerSub().equals(sub)) {
            return service;
        }
        else {
            throw new ServiceNotFoundException();
        }
    }
}
