package net.kravuar.business;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.commands.BusinessCreationCommand;
import net.kravuar.business.domain.commands.BusinessEmailVerificationCommand;
import net.kravuar.business.domain.exceptions.BusinessIncorrectEmailVerificationCodeException;
import net.kravuar.business.domain.exceptions.BusinessWithEmailAlreadyExistsException;
import net.kravuar.business.domain.exceptions.MessageSendingException;
import net.kravuar.business.ports.in.BusinessCreationUseCase;
import net.kravuar.business.ports.out.BusinessPersistencePort;
import net.kravuar.business.ports.out.EmailVerificationPort;

@RequiredArgsConstructor
public class BusinessCreationFacade implements BusinessCreationUseCase {
    private final BusinessPersistencePort businessPersistencePort;
    private final EmailVerificationPort emailVerificationPort;

    @Override
    public void sendVerification(BusinessEmailVerificationCommand command) throws MessageSendingException {
        if (businessPersistencePort.existsByEmail(command.email()))
            throw new BusinessWithEmailAlreadyExistsException(command.email());

        emailVerificationPort.sendVerificationCode(command.email());
    }

    @Override
    public Business create(BusinessCreationCommand command) {
        if (businessPersistencePort.existsByEmail(command.email()))
            throw new BusinessWithEmailAlreadyExistsException(command.email());

        // TODO: synchronization from here
        boolean verified = emailVerificationPort.verify(command.email(), command.emailVerificationCode());
        if (verified) {
            Business newBusiness = Business.builder()
                    .name(command.name())
                    .email(command.email())
                    .build();
            return businessPersistencePort.save(newBusiness);
        }
        else
            throw new BusinessIncorrectEmailVerificationCodeException();
    }
}
