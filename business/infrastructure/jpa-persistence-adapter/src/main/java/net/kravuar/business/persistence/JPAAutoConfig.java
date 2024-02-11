package net.kravuar.business.persistence;

import net.kravuar.business.ports.out.BusinessPersistencePort;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@AutoConfiguration(before = JpaRepositoriesAutoConfiguration.class)
@ConditionalOnMissingBean(BusinessPersistencePort.class)
@Import({JPARegistrar.class, JPABusinessPersistenceAdapter.class})
public class JPAAutoConfig {

    @Bean
    BusinessMapper businessMapper() {
        return BusinessMapper.INSTANCE;
    }
}