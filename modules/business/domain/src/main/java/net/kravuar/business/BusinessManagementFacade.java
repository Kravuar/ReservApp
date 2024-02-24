package net.kravuar.business;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.commands.BusinessChangeActiveCommand;
import net.kravuar.business.domain.commands.BusinessChangeNameCommand;
import net.kravuar.business.domain.commands.BusinessCreationCommand;
import net.kravuar.business.domain.exceptions.BusinessNameAlreadyTaken;
import net.kravuar.business.ports.in.BusinessManagementUseCase;
import net.kravuar.business.ports.out.BusinessNotificationPort;
import net.kravuar.business.ports.out.BusinessPersistencePort;
import net.kravuar.business.ports.out.BusinessRetrievalPort;
import net.kravuar.context.AppComponent;

@AppComponent
@RequiredArgsConstructor
public class BusinessManagementFacade implements BusinessManagementUseCase {
    private final BusinessPersistencePort businessPersistencePort;
    private final BusinessRetrievalPort businessRetrievalPort;
    private final BusinessNotificationPort businessNotificationPort;

    @Override
    public synchronized Business create(BusinessCreationCommand command) {
        if (businessRetrievalPort.existsByName(command.name()))
            throw new BusinessNameAlreadyTaken();
        Business newBusiness = Business.builder()
                .ownerSub(command.ownerSub())
                .name(command.name())
                .active(true)
                .build();
        newBusiness = businessPersistencePort.save(newBusiness);
        businessNotificationPort.notifyNewBusiness(newBusiness);
        return newBusiness;
    }

    @Override
    public void changeName(BusinessChangeNameCommand command) {
        Business business = businessRetrievalPort.findById(command.businessId());
        business.setName(command.newName());
        businessPersistencePort.save(business);
    }

    @Override
    public void changeActive(BusinessChangeActiveCommand command) {
        Business business = businessRetrievalPort.findById(command.businessId());
        business.setActive(command.active());
        businessPersistencePort.save(business);
        businessNotificationPort.notifyBusinessActiveChanged(business);
    }
}
