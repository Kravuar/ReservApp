package net.kravuar.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class GatewayConfig {

    @Bean
    RouteLocator gateway(RouteLocatorBuilder rlb) {
        return rlb
                .routes()
                .route(rs -> rs
                        .path("/business/**")
                        .uri("http://business:8081")
                )
                .route(rs -> rs
                        .path("/accounts/**")
                        .uri("http://accounts:8082")
                )
                .build();
        // TODO: remove hardcoded
    }
}
