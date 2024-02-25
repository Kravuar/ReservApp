package net.kravuar.staff.persistence.service;

import lombok.RequiredArgsConstructor;
import net.kravuar.staff.domain.Service;
import net.kravuar.staff.ports.out.ServicePersistencePort;
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
