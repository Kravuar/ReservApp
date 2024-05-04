package net.kravuar.gateway.graphql.staff;

import net.kravuar.staff.dto.StaffDTO;
import net.kravuar.staff.dto.StaffDetailsDTO;
import net.kravuar.staff.dto.StaffInvitationDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@Component
@ReactiveFeignClient(
        name = "staffManagement",
        url = "http://staff:8084/staff/api-v1/management"
)
interface StaffManagementClient {

    @PostMapping("/send-invitation/{subject}/{businessId}")
    Mono<StaffInvitationDTO> sendInvitation(@PathVariable("subject") String sub, @PathVariable("businessId") long businessId, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);

    @PostMapping("/accept-invitation/{invitationId}")
    Mono<StaffDTO> acceptInvitation(@PathVariable("invitationId") long invitationId, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);

    @PostMapping("/decline-invitation/{invitationId}")
    Mono<StaffInvitationDTO> declineInvitation(@PathVariable("invitationId") long invitationId, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);

    @PutMapping("/update-details/{staffId}")
    Mono<StaffDTO> updateDetails(@PathVariable("staffId") long staffId, @RequestBody StaffDetailsDTO details, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);

    @DeleteMapping("/remove-staff/{staffId}")
    Mono<StaffDTO> removeStaff(@PathVariable("staffId") long staffId, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);
}