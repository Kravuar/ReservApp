package net.kravuar.staff.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.staff.domain.commands.StaffAnswerInvitationCommand;
import net.kravuar.staff.domain.commands.StaffChangeDetailsCommand;
import net.kravuar.staff.domain.commands.StaffInvitationCommand;
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

    @PostMapping("/answer-invitation/{invitationId}/{accept}")
    @PreAuthorize("hasPermission(#invitationId, 'Invitation', 'AnswerInvitation')")
    void answerInvitation(@PathVariable("invitationId") long invitationId, @PathVariable("accept") boolean accept) {
        staffManagement.answerInvitation(
                new StaffAnswerInvitationCommand(
                        invitationId,
                        accept
                )
        );
    }

    @PutMapping("/update-details/{staffId}")
    @PreAuthorize("hasPermission(#staffId, 'Staff', 'Update')")
    void updateDetails(@PathVariable("staffId") long staffId, @RequestBody StaffDetailsDTO details) {
        staffManagement.changeDetails(new StaffChangeDetailsCommand(
                staffId,
                details.description()
        ));
    }

    @DeleteMapping("/remove-staff/{staffId}")
    @PreAuthorize("hasPermission(#staffId, 'Staff', 'Delete')")
    void removeStaff(@PathVariable("staffId") long staffId) {
        staffManagement.removeStaff(staffId);
    }
}
