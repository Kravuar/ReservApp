package net.kravuar.security;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
class JWTConfig {

    @Bean
    @ConditionalOnMissingBean
    @SneakyThrows
    Algorithm algorithm() {
        var generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        var keyPair = generator.generateKeyPair();
        var publicKey = (RSAPublicKey) keyPair.getPublic();
        var privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return Algorithm.RSA256(publicKey, privateKey);
    }
}
