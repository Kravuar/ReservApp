package net.kravuar.staff.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.pageable.Page;
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

    @GetMapping("/by-business/{businessId}/{page}/{pageSize}")
    Page<StaffDTO> findByBusiness(@PathVariable("businessId") long businessId, @PathVariable("page") int page, @PathVariable("pageSize") int pageSize) {
        Page<Staff> staff = staffRetrieval.findStaffByBusiness(
                businessId,
                true,
                page,
                pageSize
        );
        return new Page<>(
                staff.content().stream()
                        .map(dtoStaffMapper::staffToDTO)
                        .toList(),
                staff.totalPages()
        );
    }

    @GetMapping("/invitations-by-sub/{page}/{pageSize}")
    Page<StaffInvitationDTO> findInvitationsBySub(@AuthenticationPrincipal Jwt principal, @PathVariable("page") int page, @PathVariable("pageSize") int pageSize) {
        Page<StaffInvitation> staff = staffRetrieval.findStaffInvitationsBySubject(
                principal.getSubject(),
                page,
                pageSize
        );
        return new Page<>(
                staff.content().stream()
                        .map(dtoStaffMapper::invitationToDTO)
                        .toList(),
                staff.totalPages()
        );
    }

    @GetMapping("/invitations-by-business/{businessId}/{page}/{pageSize}")
    @PreAuthorize("isAuthenticated() && @authorizationHandler.isOwnerOfBusiness(#businessId, authentication.details.subject)")
    Page<StaffInvitationDTO> findInvitationsByBusiness(@PathVariable("businessId") long businessId, @PathVariable("page") int page, @PathVariable("pageSize") int pageSize) {
        Page<StaffInvitation> staff = staffRetrieval.findStaffInvitationsByBusiness(
                businessId,
                page,
                pageSize
        );
        return new Page<>(
                staff.content().stream()
                        .map(dtoStaffMapper::invitationToDTO)
                        .toList(),
                staff.totalPages()
        );
    }
}
