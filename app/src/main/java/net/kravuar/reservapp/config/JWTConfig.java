package net.kravuar.reservapp.config;

import net.kravuar.jwtauth.components.PrincipalExtractor;
import net.kravuar.security.IdPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
class JWTConfig {

    @Bean
    PrincipalExtractor idPrincipalExtractor() {
        return decodedJWT -> new IdPrincipal(
                decodedJWT.getClaim("id").asLong(), // TODO: externalize id claim name
                decodedJWT.getSubject(),
                decodedJWT.getToken(),
                Collections.emptySet()
        );
    }
}
