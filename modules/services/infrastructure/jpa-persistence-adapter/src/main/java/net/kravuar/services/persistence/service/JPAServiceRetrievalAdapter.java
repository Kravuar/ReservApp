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
    public Service findById(long id) {
        return servicesRepository.findById(id)
                .orElseThrow(ServiceNotFoundException::new);
    }

    @Override
    public boolean existsByName(String name) {
        return servicesRepository.existsByName(name);
    }

    @Override
    public List<Service> findAllActiveByBusinessId(long businessId) {
        return servicesRepository
                .findByBusinessIdAndActiveIsTrue(businessId).stream()
                .toList();
    }

    @Override
    public List<Service> findAllByBusinessId(long businessId) {
        return servicesRepository
                .findAllByBusinessId(businessId).stream()
                .toList();
    }

    @Override
    public List<Service> findAllActive() {
        return servicesRepository
                .findAllByActiveIsTrue().stream()
                .toList();
    }
}
