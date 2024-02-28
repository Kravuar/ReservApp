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
                .findByIdAndActive(serviceId, activeOnly)
                .orElseThrow(ServiceNotFoundException::new);
    }

    @Override
    public boolean existsActiveByName(String name) {
        return servicesRepository.existsByNameAndActiveIsTrue(name);
    }

    @Override
    public List<Service> findAllByBusinessId(long businessId, boolean activeOnly) {
        return servicesRepository
                .findAllByBusinessIdAndActive(businessId, activeOnly).stream()
                .toList();
    }

    @Override
    public List<Service> findAllActive() {
        return servicesRepository
                .findAllByActiveIsTrue().stream()
                .toList();
    }
}
