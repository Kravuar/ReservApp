package net.kravuar.commons;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;

@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class WebFlux {
    @Bean
    @Primary
    ServerHttpSecurity filterChain(ServerHttpSecurity http) {
        http.oauth2ResourceServer(customizer -> customizer.jwt(Customizer.withDefaults()));
        http.csrf(ServerHttpSecurity.CsrfSpec::disable); // TODO: this is bad, add csrf providing endpoint/header based filter
        Okta.configureResourceServer401ResponseBody(http);
        return http;
    }
}
