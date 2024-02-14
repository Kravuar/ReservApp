package net.kravuar.accounts.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
class SecurityConfig {

    @Autowired
    void authorizationServiceConfiguration(AuthenticationManagerBuilder builder, AccountDetailsService detailsService) throws Exception {
        builder.userDetailsService(detailsService);
    }
}
