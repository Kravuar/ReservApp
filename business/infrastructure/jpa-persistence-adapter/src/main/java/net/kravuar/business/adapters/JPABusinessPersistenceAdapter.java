package net.kravuar.business.adapters;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.exceptions.BusinessNotFoundException;
import net.kravuar.business.model.BusinessModel;
import net.kravuar.business.model.mappers.BusinessMapper;
import net.kravuar.business.ports.out.BusinessPersistencePort;
import net.kravuar.business.repositories.BusinessRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JPABusinessPersistenceAdapter implements BusinessPersistencePort {
    private final BusinessMapper businessMapper;
    private final BusinessRepository businessRepository;

    @Override
    public Business findById(long id) {
        return businessRepository.findById(id)
                .map(businessMapper::toDomain)
                .orElseThrow(BusinessNotFoundException::new);
    }

    @Override
    public Business save(Business business) {
        BusinessModel saved = businessRepository.save(businessMapper.toModel(business));
        return businessMapper.toDomain(saved);
    }
}
