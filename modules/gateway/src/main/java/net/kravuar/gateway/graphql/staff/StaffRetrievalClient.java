package net.kravuar.gateway.graphql.staff;

import net.kravuar.pageable.Page;
import net.kravuar.services.dto.ServiceDTO;
import net.kravuar.staff.dto.StaffDTO;
import net.kravuar.staff.dto.StaffInvitationDTO;
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
        name = "staffRetrieval",
        url = "http://staff:8084/staff/api-v1/retrieval"
)
interface StaffRetrievalClient {

    @GetMapping("/by-id/{id}")
    Mono<StaffDTO> byId(@PathVariable("id") long staffId, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);

    @GetMapping("/by-business/{businessId}/{page}/{pageSize}")
    Mono<Page<StaffDTO>> findByBusiness(@PathVariable("businessId") long businessId, @PathVariable("page") long page, @PathVariable("pageSize") long pageSize);

    @GetMapping("/my-invitations/{page}/{pageSize}")
    Mono<Page<StaffInvitationDTO>> myInvitations(@PathVariable("page") long page, @PathVariable("pageSize") long pageSize, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);

    @GetMapping("/invitations-by-business/{businessId}/{page}/{pageSize}")
    Mono<Page<StaffInvitationDTO>> findInvitationsByBusiness(@PathVariable("businessId") long businessId, @PathVariable("page") long page, @PathVariable("pageSize") long pageSize, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);

    @GetMapping("/by-ids")
    Flux<StaffDTO> byIds(@RequestParam("staffIds") Set<Long> staffIds, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);
}