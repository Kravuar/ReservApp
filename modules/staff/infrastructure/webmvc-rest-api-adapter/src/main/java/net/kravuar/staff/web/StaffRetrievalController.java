package net.kravuar.staff.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.pageable.Page;
import net.kravuar.staff.dto.DTOStaffMapper;
import net.kravuar.staff.dto.StaffDTO;
import net.kravuar.staff.dto.StaffInvitationDTO;
import net.kravuar.staff.model.StaffDetailed;
import net.kravuar.staff.model.StaffInvitation;
import net.kravuar.staff.ports.in.StaffRetrievalUseCase;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/retrieval")
@RequiredArgsConstructor
class StaffRetrievalController {
    private final StaffRetrievalUseCase staffRetrieval;
    private final DTOStaffMapper dtoMapper;

    @GetMapping("/by-id/{id}")
    StaffDTO findById(@PathVariable("id") long id) {
        return dtoMapper.staffToDTO(staffRetrieval.findStaffById(id, true));
    }

    @GetMapping("/by-business/{businessId}/{page}/{pageSize}")
    Page<StaffDTO> findByBusiness(@PathVariable("businessId") long businessId, @PathVariable("page") int page, @PathVariable("pageSize") int pageSize) {
        Page<StaffDetailed> staff = staffRetrieval.findStaffByBusiness(
                businessId,
                true,
                page,
                pageSize
        );
        return new Page<>(
                staff.content().stream()
                        .map(dtoMapper::staffToDTO)
                        .toList(),
                staff.totalElements(),
                staff.totalPages()
        );
    }

    @GetMapping("/my-invitations/{page}/{pageSize}")
    Page<StaffInvitationDTO> findMyInvitations(@AuthenticationPrincipal Jwt principal, @PathVariable("page") int page, @PathVariable("pageSize") int pageSize) {
        Page<StaffInvitation> invitations = staffRetrieval.findStaffInvitationsBySubject(
                principal.getSubject(),
                page,
                pageSize
        );
        return new Page<>(
                invitations.content().stream()
                        .map(dtoMapper::invitationToDTO)
                        .toList(),
                invitations.totalElements(),
                invitations.totalPages()
        );
    }

    @GetMapping("/invitations-by-business/{businessId}/{page}/{pageSize}")
    @PreAuthorize("hasPermission(#businessId, 'Invitation', 'Read')")
    Page<StaffInvitationDTO> findInvitationsByBusiness(@PathVariable("businessId") long businessId, @PathVariable("page") int page, @PathVariable("pageSize") int pageSize) {
        Page<StaffInvitation> invitations = staffRetrieval.findStaffInvitationsByBusiness(
                businessId,
                page,
                pageSize
        );
        return new Page<>(
                invitations.content().stream()
                        .map(dtoMapper::invitationToDTO)
                        .toList(),
                invitations.totalElements(),
                invitations.totalPages()
        );
    }

    @GetMapping("/by-ids")
    @PostFilter("filterObject.active() || principal != null && filterObject.business().ownerSub().equals(principal.username)")
    List<StaffDTO> byIds(@RequestParam("staffIds") Set<Long> staffIds) {
        return staffRetrieval.findByIds(staffIds, false).stream()
                .map(dtoMapper::staffToDTO)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
