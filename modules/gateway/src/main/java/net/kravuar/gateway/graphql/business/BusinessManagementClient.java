package net.kravuar.gateway.graphql.business;

import net.kravuar.business.dto.BusinessCreationDTO;
import net.kravuar.business.dto.BusinessDTO;
import net.kravuar.business.dto.BusinessDetailsDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@Component
@ReactiveFeignClient(
        name = "businessManagement",
        url = "http://business:8080/business/api-v1/management"
)
//
interface BusinessManagementClient {

    @PostMapping("/create")
    Mono<BusinessDTO> create(@RequestBody BusinessCreationDTO businessCreation, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);

    @PutMapping("/change-name/{businessId}")
    Mono<BusinessDTO> changeName(@PathVariable("businessId") long businessId, @RequestBody String name, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);

    @PutMapping("/change-active/{businessId}/{active}")
    Mono<BusinessDTO> changeActive(@PathVariable("businessId") long businessId, @PathVariable("active") boolean active, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);

    @PutMapping("/update-details/{businessId}")
    Mono<BusinessDTO> updateDetails(@PathVariable("businessId") long businessId, @RequestBody BusinessDetailsDTO details, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);
}
