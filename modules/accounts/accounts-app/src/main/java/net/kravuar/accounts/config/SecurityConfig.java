package net.kravuar.accounts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
class SecurityConfig {

    @Bean
    WebSecurityCustomizer customizer() {
        return web -> web.ignoring().requestMatchers(
                "/v3/api-docs*/**",
                "/swagger-ui*/**",
                "/webjars/**",
                "/error"
        );
    }
}
