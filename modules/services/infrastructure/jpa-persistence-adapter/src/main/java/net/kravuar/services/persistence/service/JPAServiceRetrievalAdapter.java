package net.kravuar.services.persistence.service;

import lombok.RequiredArgsConstructor;
import net.kravuar.services.domain.Business;
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
    public List<Service> findAllActiveByBusiness(Business business) {
        return servicesRepository
                .findByBusinessAndActiveIsTrue(business).stream()
                .toList();
    }

    @Override
    public List<Service> findAllByBusiness(Business business) {
        return servicesRepository
                .findAllByBusiness(business).stream()
                .toList();
    }

    @Override
    public List<Service> findAllActive() {
        return servicesRepository
                .findAllByActiveIsTrue().stream()
                .toList();
    }
}
