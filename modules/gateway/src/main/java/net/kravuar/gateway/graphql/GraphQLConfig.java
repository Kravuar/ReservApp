package net.kravuar.gateway.graphql;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.http.HttpHeaders;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@Configuration
@EnableReactiveFeignClients
class GraphQLConfig {

    @Bean
    WebGraphQlInterceptor intercept() {
        return (webInput, chain) -> {
            String authorization = webInput.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authorization != null) {
                webInput.configureExecutionInput((input, inputBuilder) -> inputBuilder
                        .graphQLContext(contextBuilder -> contextBuilder.put(HttpHeaders.AUTHORIZATION, authorization))
                        .build()
                );
            }
            return chain.next(webInput);
        };
    }

    @Bean
    DataFetcherExceptionResolverAdapter exceptionResolverAdapter() {
        return new ExceptionResolver();
    }
}
