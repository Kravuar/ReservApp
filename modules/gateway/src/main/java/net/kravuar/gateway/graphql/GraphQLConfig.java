package net.kravuar.gateway.graphql;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.http.HttpHeaders;

@Configuration
@EnableFeignClients
class GraphQLConfig {
    @Bean
    public WebGraphQlInterceptor intercept() {
        return (webInput, chain) -> {
            String authorization = webInput.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            webInput.configureExecutionInput((input, inputBuilder) ->
                    inputBuilder
                            .graphQLContext(contextBuilder -> contextBuilder.put(HttpHeaders.AUTHORIZATION, authorization))
                            .build()
            );
            return chain.next(webInput);
        };
    }
}
