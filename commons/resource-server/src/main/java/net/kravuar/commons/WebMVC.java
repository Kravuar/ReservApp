package net.kravuar.commons;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableWebSecurity
@EnableMethodSecurity
class WebMVC {
    @Bean
    @Primary
    HttpSecurity filterChain(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(customizer -> customizer.jwt(Customizer.withDefaults()));
        http.csrf(AbstractHttpConfigurer::disable); // TODO: this is bad, add csrf providing endpoint/header based filter
        Okta.configureResourceServer401ResponseBody(http);
        return http;
    }
}
