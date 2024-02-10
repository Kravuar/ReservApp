package net.kravuar.business;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.commands.*;
import net.kravuar.business.domain.exceptions.BusinessIncorrectEmailVerificationCodeException;
import net.kravuar.business.ports.in.BusinessManagementUseCase;
import net.kravuar.business.ports.out.BusinessPersistencePort;
import net.kravuar.business.ports.out.EmailVerificationPort;

@RequiredArgsConstructor
public class BusinessManagementFacade implements BusinessManagementUseCase {
    private final BusinessPersistencePort businessPersistencePort;
    private final EmailVerificationPort emailVerificationPort;

    @Override
    public void changeName(BusinessChangeNameCommand command) {
        Business business = businessPersistencePort.findById(command.businessId());
        business.setName(command.newName());
        businessPersistencePort.save(business);
    }

    @Override
    public void changeEmail(BusinessChangeEmailCommand command) {
        Business business = businessPersistencePort.findById(command.businessId());

        boolean verified = emailVerificationPort.verify(command.newEmail(), command.verificationCode());
        if (verified) {
            business.setEmail(command.newEmail());
            businessPersistencePort.save(business);
        }
        else
            throw new BusinessIncorrectEmailVerificationCodeException();
    }
}
