package net.kravuar.business;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.Business;
import net.kravuar.business.ports.in.BusinessRetrievalUseCase;
import net.kravuar.business.ports.out.BusinessRetrievalPort;
import net.kravuar.context.AppComponent;

import java.util.List;

@AppComponent
@RequiredArgsConstructor
public class BusinessRetrievalFacade implements BusinessRetrievalUseCase {
    private final BusinessRetrievalPort businessRetrievalPort;

    @Override
    public Business findById(long id, boolean activeOnly) {
        return businessRetrievalPort.findById(id, activeOnly);
    }

    @Override
    public List<Business> findAllBySub(String sub, boolean activeOnly) {
        return businessRetrievalPort.findBySub(sub, activeOnly);
    }

    @Override
    public List<Business> findAllActive() {
        return businessRetrievalPort.findAllActive();
    }
}
