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
    public Business findById(long id) {
        return businessRepository.findById(id)
                .orElseThrow(BusinessNotFoundException::new);
    }

    @Override
    public boolean existsByName(String name) {
        return businessRepository.existsByName(name);
    }

    @Override
    public List<Business> findActiveBySub(String sub) {
        return businessRepository.findByOwnerSubAndActiveIsTrue(sub)
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
