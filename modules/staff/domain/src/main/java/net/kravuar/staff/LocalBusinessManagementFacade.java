package net.kravuar.staff;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.staff.domain.Business;
import net.kravuar.staff.domain.commands.BusinessChangeActiveCommand;
import net.kravuar.staff.domain.commands.BusinessCreationCommand;
import net.kravuar.staff.ports.in.LocalBusinessManagementUseCase;
import net.kravuar.staff.ports.out.BusinessLockPort;
import net.kravuar.staff.ports.out.BusinessPersistencePort;
import net.kravuar.staff.ports.out.BusinessRetrievalPort;

@AppComponent
@RequiredArgsConstructor
public class LocalBusinessManagementFacade implements LocalBusinessManagementUseCase {
    private final BusinessRetrievalPort businessRetrievalPort;
    private final BusinessPersistencePort businessPersistencePort;
    private final BusinessLockPort businessLockPort;

    @Override
    public Business create(BusinessCreationCommand command) {
        return businessPersistencePort.save(Business.builder()
                .id(command.businessId())
                .ownerSub(command.ownerSub())
                .active(command.active())
                .build()
        );
    }

    @Override
    public void changeActive(BusinessChangeActiveCommand command) {
        try {
            businessLockPort.lock(command.businessId(), true);

            Business existing = businessRetrievalPort.findById(command.businessId());
            existing.setActive(command.active());
            businessPersistencePort.save(existing);
        } finally {
            businessLockPort.lock(command.businessId(), false);
        }
    }
}
