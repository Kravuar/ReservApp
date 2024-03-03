package net.kravuar.services.persistence.service;

import lombok.RequiredArgsConstructor;
import net.kravuar.services.domain.Service;
import net.kravuar.services.domain.exceptions.ServiceNotFoundException;
import net.kravuar.services.ports.out.ServiceRetrievalPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class JPAServiceRetrievalAdapter implements ServiceRetrievalPort {
    private final ServiceRepository servicesRepository;

    @Override
    public Service findById(long serviceId, boolean activeOnly) {
        return servicesRepository
                .findById(serviceId, activeOnly)
                .orElseThrow(ServiceNotFoundException::new);
    }

    @Override
    public List<Service> findAllByActiveBusinessId(long businessId, boolean activeOnly) {
        return servicesRepository
                .findAllByBusiness(businessId, activeOnly).stream()
                .toList();
    }

    @Override
    public List<Service> findAllActive() {
        return servicesRepository
                .findAllFullyActive().stream()
                .toList();
    }
}
