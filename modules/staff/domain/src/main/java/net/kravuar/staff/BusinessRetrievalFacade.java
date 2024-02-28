package net.kravuar.staff;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.staff.domain.Business;
import net.kravuar.staff.ports.in.BusinessRetrievalUseCase;
import net.kravuar.staff.ports.out.BusinessRetrievalPort;

@AppComponent
@RequiredArgsConstructor
public class BusinessRetrievalFacade implements BusinessRetrievalUseCase {
    private final BusinessRetrievalPort businessRetrievalPort;

    @Override
    public Business findById(long businessId, boolean activeOnly) {
        return businessRetrievalPort.findById(businessId, activeOnly);
    }
}
