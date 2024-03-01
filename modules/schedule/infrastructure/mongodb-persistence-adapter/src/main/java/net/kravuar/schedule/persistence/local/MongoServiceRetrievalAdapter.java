package net.kravuar.schedule.persistence.local;

import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.domain.Service;
import net.kravuar.schedule.domain.exceptions.ServiceNotFoundException;
import net.kravuar.schedule.ports.out.ServiceRetrievalPort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class MongoServiceRetrievalAdapter implements ServiceRetrievalPort {
    private final ServiceRepository serviceRepository;

    @Override
    public Service findActiveById(long serviceId) {
        return serviceRepository.findByIdAndActiveIsTrue(serviceId)
                .orElseThrow(ServiceNotFoundException::new);
    }
}
