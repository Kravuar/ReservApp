package net.kravuar.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties("authentication.jwt")
@Data
class JWTProps {
    private String issuer = "reserv-app";
    private Duration accessExpiration = Duration.ofSeconds(300);
    private Duration refreshExpiration = Duration.ofSeconds(48000);
    private String accessCookieName = "access";
    private String refreshCookieName = "refresh";
    private String idClaimName = "id";
}
