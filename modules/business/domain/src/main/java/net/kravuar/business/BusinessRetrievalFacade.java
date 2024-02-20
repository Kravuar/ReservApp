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
    public Business findById(long id) {
        return businessRetrievalPort.findById(id);
    }

    @Override
    public List<Business> findActiveBySub(String sub) {
        return businessRetrievalPort.findActiveBySub(sub);
    }

    @Override
    public List<Business> findAllActive() {
        return businessRetrievalPort.findAllActive();
    }
}
