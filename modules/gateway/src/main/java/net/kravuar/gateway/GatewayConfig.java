package net.kravuar.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@RequiredArgsConstructor
class GatewayConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/graphql")
                .allowedOrigins("*");
    }

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
                .route(rs -> rs
                        .path("/services/**")
                        .uri("http://services:8083")
                )
                .route(rs -> rs
                        .path("/staff/**")
                        .uri("http://staff:8084")
                )
                .route(rs -> rs
                        .path("/schedule/**")
                        .uri("http://schedule:8085")
                )
                .build();
        // TODO: remove hardcoded
    }
}
