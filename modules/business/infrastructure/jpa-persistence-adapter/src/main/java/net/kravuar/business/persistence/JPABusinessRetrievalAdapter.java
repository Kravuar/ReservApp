package net.kravuar.business.persistence;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.exceptions.BusinessNotFoundException;
import net.kravuar.business.model.Business;
import net.kravuar.business.ports.out.BusinessRetrievalPort;
import net.kravuar.pageable.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

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
    public Page<Business> findBySub(String sub, boolean activeOnly, int page, int pageSize) {
        var businesses = businessRepository.findByOwnerSubAndActive(
                sub,
                activeOnly,
                PageRequest.of(page, pageSize)
        );
        return new Page<>(
            businesses.getContent(),
            businesses.getTotalElements(),
            businesses.getTotalPages()
        );
    }

    @Override
    public Page<Business> findActive(int page, int pageSize) {
        var businesses = businessRepository.findByActiveIsTrue(PageRequest.of(page, pageSize));
        return new Page<>(
                businesses.getContent(),
                businesses.getTotalElements(),
                businesses.getTotalPages()
        );
    }

    @Override
    public List<Business> findByIds(Set<Long> ids, boolean activeOnly) {
        return businessRepository.findByIdsAndActive(ids, activeOnly);
    }
}
