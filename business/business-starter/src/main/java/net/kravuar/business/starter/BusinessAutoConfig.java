package net.kravuar.business.starter;

import net.kravuar.business.BusinessCreationFacade;
import net.kravuar.business.BusinessManagementFacade;
import net.kravuar.business.ports.in.BusinessCreationUseCase;
import net.kravuar.business.ports.in.BusinessManagementUseCase;
import net.kravuar.business.ports.out.BusinessPersistencePort;
import net.kravuar.business.ports.out.EmailVerificationPort;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
class BusinessAutoConfig {

    @Bean
    @ConditionalOnMissingBean(BusinessCreationUseCase.class)
    BusinessCreationUseCase businessCreationUseCase(BusinessPersistencePort persistencePort, EmailVerificationPort emailVerificationPort) {
        return new BusinessCreationFacade(persistencePort, emailVerificationPort);
    }

    @Bean
    @ConditionalOnMissingBean(BusinessManagementUseCase.class)
    BusinessManagementUseCase businessManagementUseCase(BusinessPersistencePort persistencePort, EmailVerificationPort emailVerificationPort) {
        return new BusinessManagementFacade(persistencePort, emailVerificationPort);
    }
}
