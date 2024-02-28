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
    private final BusinessRepository businessRepository;

    @Override
    public Business findById(long businessId, boolean activeOnly) {
        return businessRepository.findByIdAndActive(businessId, activeOnly)
                .orElseThrow(BusinessNotFoundException::new);
    }

    @Override
    public boolean existsActiveByName(String name) {
        return businessRepository.existsByNameAndActiveIsTrue(name);
    }

    @Override
    public List<Business> findBySub(String sub, boolean activeOnly) {
        return businessRepository.findByOwnerSubAndActive(sub, activeOnly)
                .stream()
                .toList();
    }

    @Override
    public List<Business> findAllActive() {
        return businessRepository.findAllByActiveIsTrue()
                .stream()
                .toList();
    }
}
