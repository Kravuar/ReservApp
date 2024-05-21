package net.kravuar.gateway.graphql.services;

import net.kravuar.business.dto.BusinessDTO;
import net.kravuar.pageable.Page;
import net.kravuar.services.dto.ServiceDTO;
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
        name = "serviceRetrieval",
        url = "http://services:8083/services/api-v1/retrieval"
)
interface ServiceRetrievalClient {

    @GetMapping("/my/by-business/{businessId}/{page}/{pageSize}")
    Mono<Page<ServiceDTO>> myByBusiness(@PathVariable("businessId") long businessId, @PathVariable("page") int page, @PathVariable("pageSize") int pageSize, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);

    @GetMapping("/by-business/{businessId}/{page}/{pageSize}")
    Mono<Page<ServiceDTO>> byBusiness(@PathVariable("businessId") long businessId, @PathVariable("page") int page, @PathVariable("pageSize") int pageSize);

    @GetMapping("/by-id/{serviceId}")
    Mono<ServiceDTO> byId(@PathVariable("serviceId") long serviceId, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);

    @GetMapping("/active/{page}/{pageSize}")
    Mono<Page<ServiceDTO>> allActive(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize);

    @GetMapping("/by-ids")
    Flux<ServiceDTO> byIds(@RequestParam("serviceIds") Set<Long> serviceIds, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);
}