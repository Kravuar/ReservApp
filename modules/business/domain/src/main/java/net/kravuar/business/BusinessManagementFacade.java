package net.kravuar.business;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.commands.BusinessChangeNameCommand;
import net.kravuar.business.domain.exceptions.BusinessNameAlreadyTaken;
import net.kravuar.business.ports.in.BusinessManagementUseCase;
import net.kravuar.business.ports.out.BusinessPersistencePort;
import net.kravuar.context.AppComponent;

@AppComponent
@RequiredArgsConstructor
public class BusinessManagementFacade implements BusinessManagementUseCase {
    private final BusinessPersistencePort businessPersistencePort;

    @Override
    public void changeName(BusinessChangeNameCommand command) {
        // TODO: transaction for consistency

        Business business = businessPersistencePort.findById(command.businessId());
        if (businessPersistencePort.existsByName(command.newName()))
            throw new BusinessNameAlreadyTaken();
        business.setName(command.newName());
        businessPersistencePort.save(business);
    }
}
