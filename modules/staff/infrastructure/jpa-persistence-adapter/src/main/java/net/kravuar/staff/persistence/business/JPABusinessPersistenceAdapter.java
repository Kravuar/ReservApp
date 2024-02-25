package net.kravuar.staff.persistence.business;

import lombok.RequiredArgsConstructor;
import net.kravuar.staff.domain.Business;
import net.kravuar.staff.ports.out.BusinessPersistencePort;
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
