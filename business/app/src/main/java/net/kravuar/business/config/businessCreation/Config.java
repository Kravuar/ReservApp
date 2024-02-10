package net.kravuar.business.config.businessCreation;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.BusinessCreationFacade;
import net.kravuar.business.BusinessManagementFacade;
import net.kravuar.business.ports.in.BusinessCreationUseCase;
import net.kravuar.business.ports.in.BusinessManagementUseCase;
import net.kravuar.business.ports.out.BusinessPersistencePort;
import net.kravuar.business.ports.out.EmailVerificationPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class Config {
    private final BusinessPersistencePort businessPersistencePort;
    private final EmailVerificationPort emailVerificationPort;

    @Bean
    public BusinessCreationUseCase creationUseCase() {
        return new BusinessCreationFacade(
                businessPersistencePort,
                emailVerificationPort
        );
    }
}
