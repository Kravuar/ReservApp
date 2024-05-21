package net.kravuar.gateway.graphql.business;

import net.kravuar.business.dto.BusinessDTO;
import net.kravuar.pageable.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
@ReactiveFeignClient(
        name = "businessRetrieval",
        url = "http://business:8081/business/api-v1/retrieval"
)
interface BusinessRetrievalClient {

    @GetMapping("/active/{page}/{pageSize}")
    Mono<Page<BusinessDTO>> activeBusinesses(@PathVariable("page") long page, @PathVariable("pageSize") long pageSize);

    @GetMapping("/my/{page}/{pageSize}")
    Mono<Page<BusinessDTO>> myBusinesses(@PathVariable("page") long page, @PathVariable("pageSize") long pageSize, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);

    @GetMapping("/by-id/{businessId}")
    Mono<BusinessDTO> byId(@PathVariable("businessId") long businessId, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);

    @GetMapping("/by-owner/{sub}/{page}/{pageSize}")
    Mono<Page<BusinessDTO>> byOwner(@PathVariable("sub") String sub, @PathVariable("page") long page, @PathVariable("pageSize") long pageSize);

    @GetMapping("/by-ids")
    Flux<BusinessDTO> byIds(@RequestParam("businessIds") Set<Long> businessIds, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);
}
