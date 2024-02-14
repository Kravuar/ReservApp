package net.kravuar.business.persistence;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.exceptions.BusinessNotFoundException;
import net.kravuar.business.ports.out.BusinessPersistencePort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class JPABusinessPersistenceAdapter implements BusinessPersistencePort {
    private final BusinessMapper businessMapper;
    private final BusinessRepository businessRepository;

    @Override
    public Business findById(long id) {
        return businessRepository.findById(id)
                .map(businessMapper::toDomain)
                .orElseThrow(BusinessNotFoundException::new);
    }

    @Override
    public boolean existsByName(String name) {
        return businessRepository.existsByName(name);
    }

    @Override
    public Business save(Business business) {
        BusinessModel saved = businessRepository.save(businessMapper.toModel(business));
        return businessMapper.toDomain(saved);
    }
}
