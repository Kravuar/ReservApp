package net.kravuar.gateway.graphql;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BatchHelper {

    public static <S, R, ID> Mono<Map<S, R>> mapEntitiesToRelatedData(
            List<S> entities,
            Function<R, ID> resultIdExtractor,
            Function<S, ID> resultIdFromSourceExtractor,
            Function<Set<ID>, Flux<R>> retrievalFunction
    ) {
        Set<ID> ids = entities.stream()
                .map(resultIdFromSourceExtractor)
                .collect(Collectors.toSet());

        return retrievalFunction.apply(ids)
                .collectMap(
                        resultIdExtractor,
                        Function.identity()
                )
                .map(dataMap -> entities.stream().collect(Collectors.toMap(
                                Function.identity(),
                                entity -> dataMap.get(resultIdFromSourceExtractor.apply(entity))
                        ))
                );
    }
}
