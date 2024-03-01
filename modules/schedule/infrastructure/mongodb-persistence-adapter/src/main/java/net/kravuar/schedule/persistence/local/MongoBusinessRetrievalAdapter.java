package net.kravuar.schedule.persistence.local;

import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.domain.Business;
import net.kravuar.schedule.domain.exceptions.BusinessNotFoundException;
import net.kravuar.schedule.ports.out.BusinessRetrievalPort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class MongoBusinessRetrievalAdapter implements BusinessRetrievalPort {
    private final BusinessRepository businessRepository;

    @Override
    public Business findActiveById(long businessId) {
        return businessRepository.findByIdAndActiveIsTrue(businessId)
                .orElseThrow(BusinessNotFoundException::new);
    }
}
