package net.kravuar.commons;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Configuration
@EnableWebSecurity
@EnableResourceServer
@EnableMethodSecurity
class ResourceServerConfig {

    @Bean
    @Primary
    HttpSecurity filterChain(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(customizer -> customizer.jwt(Customizer.withDefaults()));
        Okta.configureResourceServer401ResponseBody(http);
        return http;
    }
}