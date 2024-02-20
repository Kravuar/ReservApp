package net.kravuar.services.persistence;

import lombok.RequiredArgsConstructor;
import net.kravuar.services.domain.Service;
import net.kravuar.services.domain.exceptions.ServiceNameAlreadyTaken;
import net.kravuar.services.ports.out.ServicePersistencePort;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class JPAServicePersistenceAdapter implements ServicePersistencePort {
    private final ServiceMapper servicesMapper;
    private final ServiceRepository servicesRepository;

    @Override
    public Service save(Service services) {
        try {
            ServiceModel saved = servicesRepository.save(servicesMapper.toModel(services));
            return servicesMapper.toDomain(saved);
        } catch (DataIntegrityViolationException e) {
            // TODO: seems to be error prone
            throw new ServiceNameAlreadyTaken();
        }
    }
}
