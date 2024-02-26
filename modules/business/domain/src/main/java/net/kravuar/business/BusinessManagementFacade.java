package net.kravuar.business;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.commands.BusinessChangeActiveCommand;
import net.kravuar.business.domain.commands.BusinessChangeDetailsCommand;
import net.kravuar.business.domain.commands.BusinessChangeNameCommand;
import net.kravuar.business.domain.commands.BusinessCreationCommand;
import net.kravuar.business.domain.exceptions.BusinessNameAlreadyTaken;
import net.kravuar.business.ports.in.BusinessManagementUseCase;
import net.kravuar.business.ports.out.BusinessLockPort;
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
    private final BusinessLockPort businessLockPort;

    @Override
    public Business create(BusinessCreationCommand command) {
        try {
            businessLockPort.lock(command.name(), true);

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
        } finally {
            businessLockPort.lock(command.name(), false);
        }
    }

    @Override
    public void changeName(BusinessChangeNameCommand command) {
        try {
            businessLockPort.lock(command.businessId(), true);
            businessLockPort.lock(command.newName(), true);

            if (businessRetrievalPort.existsByName(command.newName()))
                throw new BusinessNameAlreadyTaken();
            Business business = businessRetrievalPort.findById(command.businessId());
            business.setName(command.newName());
            businessPersistencePort.save(business);
        } finally {
            businessLockPort.lock(command.newName(), false);
            businessLockPort.lock(command.businessId(), false);
        }
    }

    @Override
    public void changeActive(BusinessChangeActiveCommand command) {
        try {
            businessLockPort.lock(command.businessId(), true);

            Business business = businessRetrievalPort.findById(command.businessId());
            business.setActive(command.active());
            businessPersistencePort.save(business);
            businessNotificationPort.notifyBusinessActiveChanged(business);
        } finally {
            businessLockPort.lock(command.businessId(), false);
        }
    }

    @Override
    public void changeDetails(BusinessChangeDetailsCommand command) {
        try {
            businessLockPort.lock(command.businessId(), true);

            Business business = businessRetrievalPort.findById(command.businessId());
            business.setDescription(command.description());
            businessPersistencePort.save(business);
        } finally {
            businessLockPort.lock(command.businessId(), false);
        }
    }
}
