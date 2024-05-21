package net.kravuar.services.persistence.service;

import lombok.RequiredArgsConstructor;
import net.kravuar.pageable.Page;
import net.kravuar.services.domain.exceptions.ServiceNotFoundException;
import net.kravuar.services.model.Service;
import net.kravuar.services.ports.out.ServiceRetrievalPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

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
    public Page<Service> findActiveByActiveBusinessId(long businessId, boolean activeOnly, int page, int pageSize) {
        var services = servicesRepository
                .findByBusiness(
                        businessId,
                        activeOnly,
                        PageRequest.of(page, pageSize)
                );
        return new Page<>(
                services.getContent(),
                services.getTotalElements(),
                services.getTotalPages()
        );
    }

    @Override
    public Page<Service> findActive(int page, int pageSize) {
        var services = servicesRepository.findFullyActive(PageRequest.of(page, pageSize));
        return new Page<>(
                services.getContent(),
                services.getTotalElements(),
                services.getTotalPages()
        );
    }

    @Override
    public List<Service> findByIds(Set<Long> serviceIds, boolean activeOnly) {
        return servicesRepository.findByIdsAndActive(serviceIds, activeOnly);
    }
}
