package net.kravuar.business.persistence;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.exceptions.BusinessNotFoundException;
import net.kravuar.business.ports.out.BusinessRetrievalPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class JPABusinessRetrievalAdapter implements BusinessRetrievalPort {
    private final BusinessMapper businessMapper;
    private final BusinessRepository businessRepository;

    @Override
    public Business findById(long id) {
        return businessRepository.findById(id)
                .map(businessMapper::toDomain)
                .orElseThrow(BusinessNotFoundException::new);
    }

    @Override
    public List<Business> findActiveBySub(String sub) {
        return businessRepository.findByOwnerSubAndActiveIsTrue(sub)
                .stream()
                .map(businessMapper::toDomain)
                .toList();
    }

    @Override
    public List<Business> findAllActive() {
        return businessRepository.findAllByActiveIsTrue()
                .stream()
                .map(businessMapper::toDomain)
                .toList();
    }
}
