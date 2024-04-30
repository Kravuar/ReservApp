package net.kravuar.business;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.model.Business;
import net.kravuar.business.ports.in.BusinessRetrievalUseCase;
import net.kravuar.business.ports.out.BusinessRetrievalPort;
import net.kravuar.context.AppComponent;
import net.kravuar.pageable.Page;

@AppComponent
@RequiredArgsConstructor
public class BusinessRetrievalFacade implements BusinessRetrievalUseCase {
    private final BusinessRetrievalPort businessRetrievalPort;

    @Override
    public Business findById(long id, boolean activeOnly) {
        return businessRetrievalPort.findById(id, activeOnly);
    }

    @Override
    public Page<Business> findBySub(String sub, boolean activeOnly, int page, int pageSize) {
        return businessRetrievalPort.findBySub(sub, activeOnly, page, pageSize);
    }

    @Override
    public Page<Business> findActive(int page, int pageSize) {
        return businessRetrievalPort.findActive(page, pageSize);
    }
}
