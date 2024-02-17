package net.kravuar.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayServer {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServer.class, args);
    }

    @Bean
    RouteLocator gateway(RouteLocatorBuilder rlb) {
        return rlb
                .routes()
                .route(rs -> rs
                        .path("/accounts/**")
                        .filters(GatewayFilterSpec::tokenRelay)
                        .uri("http://accounts:8081") // accounts
                )
                .route(rs -> rs
                        .path("/business/**")
                        .filters(GatewayFilterSpec::tokenRelay)
                        .uri("http://business:8082") // business
                )
                .build();
        // TODO: remove hardcoded
    }
}