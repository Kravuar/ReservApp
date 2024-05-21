package net.kravuar.staff.persistence.local;

import lombok.RequiredArgsConstructor;
import net.kravuar.staff.domain.Business;
import net.kravuar.staff.domain.exceptions.BusinessNotFoundException;
import net.kravuar.staff.ports.out.BusinessRetrievalPort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class JPABusinessRetrievalAdapter implements BusinessRetrievalPort {
    private final BusinessRepository businessRepository;

    @Override
    public Business findById(long businessId) {
        return businessRepository.findByIdAndActiveIsTrue(businessId)
                .orElseThrow(BusinessNotFoundException::new);
    }
}
