package net.kravuar.business;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.Business;
import net.kravuar.business.ports.in.BusinessRetrievalUseCase;
import net.kravuar.business.ports.out.BusinessPersistencePort;
import net.kravuar.context.AppComponent;

@AppComponent
@RequiredArgsConstructor
public class BusinessRetrievalFacade implements BusinessRetrievalUseCase {
    private final BusinessPersistencePort businessPersistencePort;

    @Override
    public Business findById(long id) {
        return businessPersistencePort.findById(id);
    }
}
