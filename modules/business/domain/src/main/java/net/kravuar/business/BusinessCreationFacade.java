package net.kravuar.business;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.commands.BusinessCreationCommand;
import net.kravuar.business.domain.exceptions.BusinessNameAlreadyTaken;
import net.kravuar.business.domain.exceptions.EmailNotVerifiedException;
import net.kravuar.business.ports.in.BusinessCreationUseCase;
import net.kravuar.business.ports.out.AccountCheckingPort;
import net.kravuar.business.ports.out.BusinessPersistencePort;
import net.kravuar.context.AppComponent;

@AppComponent
@RequiredArgsConstructor
public class BusinessCreationFacade implements BusinessCreationUseCase {
    private final BusinessPersistencePort businessPersistencePort;
    private final AccountCheckingPort accountCheckingPort;

    @Override
    public Business create(BusinessCreationCommand command) {
        // TODO: transaction for consistency

        if (!accountCheckingPort.hasVerifiedEmail(command.ownerId()))
            throw new EmailNotVerifiedException();
        if (businessPersistencePort.existsByName(command.name()))
            throw new BusinessNameAlreadyTaken();

        Business newBusiness = Business.builder()
                .ownerId(command.ownerId())
                .name(command.name())
                .build();
        return businessPersistencePort.save(newBusiness);
    }
}
