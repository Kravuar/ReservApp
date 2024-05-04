package net.kravuar.gateway.graphql;

import feign.FeignException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.lang.NonNull;

class ExceptionResolver extends DataFetcherExceptionResolverAdapter {
    @Override
    public GraphQLError resolveToSingleError(@NonNull Throwable e, @NonNull DataFetchingEnvironment env) {
        if (e instanceof FeignException) {
            return handleFeign(e, env);
        } else {
            return super.resolveToSingleError(e, env);
        }
    }

    private GraphQLError handleFeign(@NonNull Throwable e, @NonNull DataFetchingEnvironment env) {
        FeignException feignException = (FeignException) e;

        return GraphqlErrorBuilder.newError(env)
                .message("Feign client error: " + feignException.getMessage())
                .errorType(getErrorType(feignException.status()))
                .build();
    }

    private ErrorType getErrorType(int httpStatus) {
        return switch (httpStatus) {
            case 400 -> ErrorType.BAD_REQUEST;
            case 401 -> ErrorType.UNAUTHORIZED;
            case 403 -> ErrorType.FORBIDDEN;
            default -> ErrorType.INTERNAL_ERROR;
        };
    }
}
