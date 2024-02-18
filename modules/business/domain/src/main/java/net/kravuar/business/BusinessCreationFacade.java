package net.kravuar.business;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.commands.BusinessCreationCommand;
import net.kravuar.business.domain.exceptions.BusinessNameAlreadyTaken;
import net.kravuar.business.ports.in.BusinessCreationUseCase;
import net.kravuar.business.ports.out.BusinessPersistencePort;
import net.kravuar.context.AppComponent;

@AppComponent
@RequiredArgsConstructor
public class BusinessCreationFacade implements BusinessCreationUseCase {
    private final BusinessPersistencePort businessPersistencePort;
    @Override
    public Business create(BusinessCreationCommand command) {
        // TODO: transaction for consistency

        if (businessPersistencePort.existsByName(command.name()))
            throw new BusinessNameAlreadyTaken();

        Business newBusiness = Business.builder()
                .ownerSub(command.ownerSub())
                .name(command.name())
                .build();
        return businessPersistencePort.save(newBusiness);
    }
}
