package net.kravuar.business.config;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.BusinessManagementFacade;
import net.kravuar.business.ports.in.BusinessManagementUseCase;
import net.kravuar.business.ports.out.BusinessPersistencePort;
import net.kravuar.business.ports.out.EmailVerificationPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class UseCasesConfig {
    private final BusinessPersistencePort businessPersistencePort;
    private final EmailVerificationPort emailVerificationPort;

    @Bean
    public BusinessManagementUseCase managementUseCase() {
        return new BusinessManagementFacade(
                businessPersistencePort,
                emailVerificationPort
        );
    }
}
