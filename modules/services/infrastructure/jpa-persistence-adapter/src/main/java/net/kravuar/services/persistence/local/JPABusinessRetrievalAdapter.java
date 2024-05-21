package net.kravuar.services.persistence.local;

import lombok.RequiredArgsConstructor;
import net.kravuar.services.domain.Business;
import net.kravuar.services.domain.exceptions.BusinessNotFoundException;
import net.kravuar.services.ports.out.BusinessRetrievalPort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class JPABusinessRetrievalAdapter implements BusinessRetrievalPort {
    private final BusinessRepository businessRepository;

    @Override
    public Business findActiveById(long businessId) {
        return businessRepository.findByIdAndActiveIsTrue(businessId)
                .orElseThrow(BusinessNotFoundException::new);
    }
}
