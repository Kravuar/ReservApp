package net.kravuar.services.persistence.service;

import lombok.RequiredArgsConstructor;
import net.kravuar.services.model.Service;
import net.kravuar.services.ports.out.ServicePersistencePort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class JPAServicePersistenceAdapter implements ServicePersistencePort {
    private final ServiceRepository servicesRepository;

    @Override
    public Service save(Service service) {
        return servicesRepository.save(service);
    }
}
