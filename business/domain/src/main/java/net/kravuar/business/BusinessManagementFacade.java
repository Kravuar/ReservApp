package net.kravuar.business;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.commands.BusinessChangeEmailCommand;
import net.kravuar.business.domain.commands.BusinessChangeNameCommand;
import net.kravuar.business.domain.commands.BusinessCreationCommand;
import net.kravuar.business.domain.commands.BusinessEmailVerificationCommand;
import net.kravuar.business.domain.exceptions.MessageSendingException;
import net.kravuar.business.ports.in.BusinessManagementUseCase;
import net.kravuar.business.ports.out.BusinessPersistencePort;
import net.kravuar.business.ports.out.EmailVerificationPort;

@RequiredArgsConstructor
public class BusinessManagementFacade implements BusinessManagementUseCase {
    private final BusinessPersistencePort businessPersistencePort;
    private final EmailVerificationPort emailVerificationPort;

    @Override
    public Business create(BusinessCreationCommand command) throws MessageSendingException {
        emailVerificationPort.sendVerificationMessage(command.email());
        return businessPersistencePort.save(Business.builder()
                .name(command.name())
                .email(command.email())
                .build()
        );
    }

    @Override
    public void changeName(BusinessChangeNameCommand command) {
        Business business = businessPersistencePort.findById(command.businessId());
        business.setName(command.newName());
        businessPersistencePort.save(business);
    }

    @Override
    public void changeEmail(BusinessChangeEmailCommand command) throws MessageSendingException {
        Business business = businessPersistencePort.findById(command.businessId());
        business.setEmail(command.newEmail());
        business.setEmailVerified(false);
        emailVerificationPort.sendVerificationMessage(command.newEmail());
        businessPersistencePort.save(business);
    }

    @Override
    public boolean verifyEmail(BusinessEmailVerificationCommand command) {
        Business business = businessPersistencePort.findById(command.businessId());
        boolean verified = emailVerificationPort.verify(business.getEmail(), command.verificationCode());
        if (verified) {
            business.setEmailVerified(true);
            businessPersistencePort.save(business);
        }
        return verified;
    }
}
