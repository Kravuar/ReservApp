package net.kravuar.business.persistence;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.model.Business;
import net.kravuar.business.ports.out.BusinessPersistencePort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class JPABusinessPersistenceAdapter implements BusinessPersistencePort {
    private final BusinessRepository businessRepository;

    @Override
    public Business save(Business business) {
        return businessRepository.save(business);
    }
}
