package net.kravuar.business;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.exceptions.BusinessNotFoundException;
import net.kravuar.business.model.Business;
import net.kravuar.business.ports.in.BusinessRetrievalUseCase;
import net.kravuar.business.ports.out.BusinessRetrievalPort;
import net.kravuar.context.AppComponent;
import net.kravuar.pageable.Page;

import java.util.List;
import java.util.Set;

@AppComponent
@RequiredArgsConstructor
public class BusinessRetrievalFacade implements BusinessRetrievalUseCase {
    private final BusinessRetrievalPort businessRetrievalPort;

    @Override
    public Business findById(long businessId, boolean activeOnly) {
        return businessRetrievalPort.findById(businessId, true);
    }

    @Override
    public Business findByIdAndSub(long businessId, String sub) {
        Business business = businessRetrievalPort.findById(businessId, false);
        if (!business.isActive() && business.getOwnerSub().equals(sub)) {
            return business;
        }
        else {
            throw new BusinessNotFoundException();
        }
    }

    @Override
    public Page<Business> findBySub(String sub, boolean activeOnly, int page, int pageSize) {
        return businessRetrievalPort.findBySub(sub, activeOnly, page, pageSize);
    }

    @Override
    public Page<Business> findActive(int page, int pageSize) {
        return businessRetrievalPort.findActive(page, pageSize);
    }

    @Override
    public List<Business> findByIds(Set<Long> businessIds, boolean activeOnly) {
        return businessRetrievalPort.findByIds(businessIds, activeOnly);
    }
}
