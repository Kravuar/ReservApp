package net.kravuar.business;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.commands.BusinessChangeNameCommand;
import net.kravuar.business.ports.in.BusinessManagementUseCase;
import net.kravuar.business.ports.out.BusinessPersistencePort;
import net.kravuar.business.ports.out.BusinessRetrievalPort;
import net.kravuar.context.AppComponent;

@AppComponent
@RequiredArgsConstructor
public class BusinessManagementFacade implements BusinessManagementUseCase {
    private final BusinessPersistencePort businessPersistencePort;
    private final BusinessRetrievalPort businessRetrievalPort;

    @Override
    public void changeName(BusinessChangeNameCommand command) {
        Business business = businessRetrievalPort.findById(command.businessId());
        business.setName(command.newName());
        businessPersistencePort.save(business);
    }
}
