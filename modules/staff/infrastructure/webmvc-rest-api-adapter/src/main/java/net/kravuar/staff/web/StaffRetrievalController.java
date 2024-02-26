package net.kravuar.staff.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.domain.StaffInvitation;
import net.kravuar.staff.ports.in.StaffRetrievalUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/retrieval")
@RequiredArgsConstructor
class StaffRetrievalController {
    private final StaffRetrievalUseCase staffRetrieval;

    @GetMapping("/by-id/{id}")
    public Staff findById(@PathVariable("id") long id) {
        return staffRetrieval.findStaffById(id);
    }

    @GetMapping("/by-business/{businessId}")
    public List<Staff> findByBusiness(@PathVariable("businessId") long businessId) {
        return staffRetrieval.findAllStaffByBusiness(businessId);
    }

    @GetMapping("/invitations-by-sub")
    public List<StaffInvitation> findInvitationsBySub(@AuthenticationPrincipal Jwt principal) {
        return staffRetrieval.findStaffInvitationsBySubject(principal.getSubject());
    }

    @GetMapping("/invitations-by-business/{businessId}")
    @PreAuthorize("isAuthenticated() && @authorizationHandler.isOwnerOfBusiness(#businessId, authentication.details.subject)")
    public List<StaffInvitation> findInvitationsByBusiness(@PathVariable("businessId") long businessId) {
        return staffRetrieval.findStaffInvitationsByBusiness(businessId);
    }
}
