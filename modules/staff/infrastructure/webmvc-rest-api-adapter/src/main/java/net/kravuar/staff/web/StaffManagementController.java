package net.kravuar.staff.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.staff.domain.commands.RemoveStaffCommand;
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
    @PreAuthorize("isAuthenticated() && @authorizationHandler.isOwnerOfBusiness(#businessId, authentication.details.subject)")
    StaffInvitationDTO sendInvitation(@PathVariable("subject") String sub, @PathVariable("businessId") long businessId) {
        return dtoStaffMapper.invitationToDTO(staffManagement.sendInvitation(
                new StaffInvitationCommand(
                        sub,
                        businessId
                )
        ));
    }

    @PostMapping("/answer-invitation/{invitationId}/{accept}")
    @PreAuthorize("isAuthenticated() && @authorizationHandler.isInvitedStaff(#invitationId, authentication.details.subject)")
    void answerInvitation(@PathVariable("invitationId") long invitationId, @PathVariable("accept") boolean accept) {
        staffManagement.answerInvitation(
                new StaffAnswerInvitationCommand(
                        invitationId,
                        accept
                )
        );
    }

    @PutMapping("/update-details")
    @PreAuthorize("isAuthenticated() && @authorizationHandler.isStaffOrOwnerOfStaffBusiness(#command.staffId(), authentication.details.subject)")
    void updateDetails(@RequestBody StaffChangeDetailsCommand command) {
        staffManagement.changeDetails(command);
    }

    @DeleteMapping("/remove-staff")
    @PreAuthorize("isAuthenticated() && @authorizationHandler.isOwnerOfStaffBusiness(#command.staffId(), authentication.details.subject)")
    void removeStaff(@RequestBody RemoveStaffCommand command) {
        staffManagement.removeStaff(command);
    }
}
