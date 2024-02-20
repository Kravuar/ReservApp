package net.kravuar.business.persistence;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.exceptions.BusinessNameAlreadyTaken;
import net.kravuar.business.ports.out.BusinessPersistencePort;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class JPABusinessPersistenceAdapter implements BusinessPersistencePort {
    private final BusinessMapper businessMapper;
    private final BusinessRepository businessRepository;

    @Override
    public Business save(Business business) {
        try {
            BusinessModel saved = businessRepository.save(businessMapper.toModel(business));
            return businessMapper.toDomain(saved);
        } catch (DataIntegrityViolationException e) {
            // TODO: seems to be error prone
            throw new BusinessNameAlreadyTaken();
        }
    }
}
