package net.kravuar.schedule;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.schedule.ports.in.BusinessRetrievalUseCase;
import net.kravuar.schedule.ports.out.BusinessRetrievalPort;
import net.kravuar.staff.model.Business;

@AppComponent
@RequiredArgsConstructor
public class BusinessRetrievalFacade implements BusinessRetrievalUseCase {
    private final BusinessRetrievalPort businessRetrievalPort;

    @Override
    public Business findById(long businessId) {
        return businessRetrievalPort.findActiveById(businessId);
    }
}
