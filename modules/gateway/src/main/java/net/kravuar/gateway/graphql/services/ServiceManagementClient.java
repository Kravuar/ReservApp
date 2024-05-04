package net.kravuar.gateway.graphql.services;

import net.kravuar.services.domain.commands.ServiceCreationCommand;
import net.kravuar.services.dto.ServiceDTO;
import net.kravuar.services.dto.ServiceDetailsDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@Component
@ReactiveFeignClient(
        name = "serviceManagement",
        url = "http://services:8083/service/api-v1/management"
)
interface ServiceManagementClient {

    @PostMapping("/create/{businessId}")
    Mono<ServiceDTO> create(@RequestBody ServiceCreationCommand command, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);

    @PutMapping("/change-active/{serviceId}/{active}")
    Mono<ServiceDTO> changeActive(@PathVariable("serviceId") long serviceId, @PathVariable("active") boolean active, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);

    @PutMapping("/update-details/{serviceId}")
    Mono<ServiceDTO> updateDetails(@PathVariable("serviceId") long serviceId, @RequestBody ServiceDetailsDTO details, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);
}