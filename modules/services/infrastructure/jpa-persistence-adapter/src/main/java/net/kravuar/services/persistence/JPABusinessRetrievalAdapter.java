package net.kravuar.services.persistence;

import lombok.RequiredArgsConstructor;
import net.kravuar.services.domain.Business;
import net.kravuar.services.domain.exceptions.ServiceNotFoundException;
import net.kravuar.services.ports.out.BusinessRetrievalPort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class JPABusinessRetrievalAdapter implements BusinessRetrievalPort {
    private final BusinessRepository businessRepository;
    private final BusinessMapper businessMapper;

    @Override
    public Business findById(long id) {
        return businessRepository.findById(id)
                .map(businessMapper::toDomain)
                .orElseThrow(ServiceNotFoundException::new);
    }
}
