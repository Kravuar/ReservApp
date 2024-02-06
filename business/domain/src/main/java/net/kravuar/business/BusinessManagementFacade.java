package net.kravuar.business;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.commands.BusinessCreationCommand;
import net.kravuar.business.domain.commands.BusinessEditNameCommand;
import net.kravuar.business.domain.commands.BusinessEmailChangeCommand;
import net.kravuar.business.domain.exceptions.MessageSendingException;
import net.kravuar.business.ports.in.BusinessManagementUseCase;
import net.kravuar.business.ports.out.BusinessPersistencePort;
import net.kravuar.business.ports.out.EmailVerificationPort;

@RequiredArgsConstructor
public class BusinessManagementFacade implements BusinessManagementUseCase {
    private final BusinessPersistencePort businessPersistencePort;
    private final EmailVerificationPort emailVerificationPort;

    @Override
    public Business create(@Valid BusinessCreationCommand command) throws MessageSendingException {
        emailVerificationPort.sendVerificationMessage(command.email());
        return businessPersistencePort.save(Business.builder()
                .name(command.name())
                .email(command.email())
                .build()
        );
    }

    @Override
    public void editName(@Valid BusinessEditNameCommand command) {
        Business business = businessPersistencePort.findById(command.businessId());
        business.setName(command.newName());
        businessPersistencePort.save(business);
    }

    @Override
    public void changeEmail(@Valid BusinessEmailChangeCommand command) throws MessageSendingException {
        Business business = businessPersistencePort.findById(command.businessId());
        business.setEmail(command.newEmail());
        emailVerificationPort.sendVerificationMessage(command.newEmail());
        businessPersistencePort.save(business);
    }
}
