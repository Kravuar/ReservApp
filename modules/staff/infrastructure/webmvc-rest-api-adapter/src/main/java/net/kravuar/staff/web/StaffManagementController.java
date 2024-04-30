package net.kravuar.staff.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.staff.domain.commands.StaffChangeDetailsCommand;
import net.kravuar.staff.domain.commands.StaffInvitationCommand;
import net.kravuar.staff.dto.DTOStaffMapper;
import net.kravuar.staff.dto.StaffDTO;
import net.kravuar.staff.dto.StaffDetailsDTO;
import net.kravuar.staff.dto.StaffInvitationDTO;
import net.kravuar.staff.model.StaffDetailed;
import net.kravuar.staff.ports.in.StaffManagementUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/management")
@RequiredArgsConstructor
class StaffManagementController {
    private final StaffManagementUseCase staffManagement;
    private final DTOStaffMapper dtoStaffMapper;

    @PostMapping("/send-invitation/{subject}/{businessId}")
    @PreAuthorize("hasPermission(#businessId, 'Invitation', 'Invite')")
    StaffInvitationDTO sendInvitation(@PathVariable("subject") String sub, @PathVariable("businessId") long businessId) {
        return dtoStaffMapper.invitationToDTO(staffManagement.sendInvitation(
                new StaffInvitationCommand(
                        sub,
                        businessId
                )
        ));
    }

    @PostMapping("/accept-invitation/{invitationId}")
    @PreAuthorize("hasPermission(#invitationId, 'Invitation', 'AcceptInvitation')")
    StaffDTO acceptInvitation(@PathVariable("invitationId") long invitationId) {
        StaffDetailed createdStaff = staffManagement.acceptInvitation(invitationId);

        return dtoStaffMapper.staffToDTO(createdStaff);
    }

    @PostMapping("/decline-invitation/{invitationId}")
    @PreAuthorize("hasPermission(#invitationId, 'Invitation', 'DeclineInvitation')")
    StaffInvitationDTO declineInvitation(@PathVariable("invitationId") long invitationId) {
        return dtoStaffMapper.invitationToDTO(staffManagement.declineInvitation(invitationId));
    }

    @PutMapping("/update-details/{staffId}")
    @PreAuthorize("hasPermission(#staffId, 'Staff', 'Update')")
    StaffDTO updateDetails(@PathVariable("staffId") long staffId, @RequestBody StaffDetailsDTO details) {
        return dtoStaffMapper.staffToDTO(
                staffManagement.changeDetails(new StaffChangeDetailsCommand(
                        staffId,
                        details.description()
                ))
        );
    }

    @DeleteMapping("/remove-staff/{staffId}")
    @PreAuthorize("hasPermission(#staffId, 'Staff', 'Delete')")
    StaffDTO removeStaff(@PathVariable("staffId") long staffId) {
        return dtoStaffMapper.staffToDTO(staffManagement.removeStaff(staffId));
    }
}
