package net.kravuar.staff.web;

import lombok.RequiredArgsConstructor;
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
    private final DTOStaffMapper dtoStaffMapper;

    @GetMapping("/by-id/{id}")
    StaffDTO findById(@PathVariable("id") long id) {
        return dtoStaffMapper.staffToDTO(staffRetrieval.findStaffById(id, true));
    }

    @GetMapping("/by-business/{businessId}")
    List<StaffDTO> findByBusiness(@PathVariable("businessId") long businessId) {
        return staffRetrieval.findAllStaffByBusiness(businessId, true).stream()
                .map(dtoStaffMapper::staffToDTO)
                .toList();
    }

    @GetMapping("/invitations-by-sub")
    List<StaffInvitationDTO> findInvitationsBySub(@AuthenticationPrincipal Jwt principal) {
        return staffRetrieval.findStaffInvitationsBySubject(principal.getSubject()).stream()
                .map(dtoStaffMapper::invitationToDTO)
                .toList();
    }

    @GetMapping("/invitations-by-business/{businessId}")
    @PreAuthorize("isAuthenticated() && @authorizationHandler.isOwnerOfBusiness(#businessId, authentication.details.subject)")
    List<StaffInvitationDTO> findInvitationsByBusiness(@PathVariable("businessId") long businessId) {
        return staffRetrieval.findStaffInvitationsByBusiness(businessId).stream()
                .map(dtoStaffMapper::invitationToDTO)
                .toList();
    }
}
