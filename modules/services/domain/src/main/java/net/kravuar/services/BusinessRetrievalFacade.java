package net.kravuar.services;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.services.domain.Business;
import net.kravuar.services.ports.in.BusinessRetrievalUseCase;
import net.kravuar.services.ports.out.BusinessRetrievalPort;

@AppComponent
@RequiredArgsConstructor
public class BusinessRetrievalFacade implements BusinessRetrievalUseCase {
    private final BusinessRetrievalPort businessRetrievalPort;

    @Override
    public Business findById(long id) {
        return businessRetrievalPort.findById(id);
    }
}
