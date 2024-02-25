package net.kravuar.services.persistence.business;

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
    public Business findById(long id) {
        return businessRepository.findById(id)
                .orElseThrow(BusinessNotFoundException::new);
    }
}
