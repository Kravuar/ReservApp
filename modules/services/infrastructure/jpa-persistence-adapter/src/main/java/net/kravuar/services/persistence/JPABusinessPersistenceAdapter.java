package net.kravuar.services.persistence;

import lombok.RequiredArgsConstructor;
import net.kravuar.services.domain.Business;
import net.kravuar.services.ports.out.BusinessPersistencePort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class JPABusinessPersistenceAdapter implements BusinessPersistencePort {
    private final BusinessMapper businessMapper;
    private final BusinessRepository businessRepository;

    @Override
    public Business save(Business business) {
        BusinessModel saved = businessRepository.save(businessMapper.toModel(business));
        return businessMapper.toDomain(saved);
    }
}
