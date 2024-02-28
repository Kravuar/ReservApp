package net.kravuar.schedule;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.schedule.domain.Business;
import net.kravuar.schedule.ports.in.BusinessRetrievalUseCase;
import net.kravuar.schedule.ports.out.BusinessRetrievalPort;

@AppComponent
@RequiredArgsConstructor
public class BusinessRetrievalFacade implements BusinessRetrievalUseCase {
    private final BusinessRetrievalPort businessRetrievalPort;

    @Override
    public Business findActiveById(long businessId) {
        return businessRetrievalPort.findActiveById(businessId);
    }
}
