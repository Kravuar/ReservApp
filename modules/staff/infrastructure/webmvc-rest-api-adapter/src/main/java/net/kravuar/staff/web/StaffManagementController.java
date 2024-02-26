package net.kravuar.staff.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.staff.domain.StaffInvitation;
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

    @PostMapping("/send-invitation/{subject}/{businessId}")
    @PreAuthorize("isAuthenticated() && @authorizationHandler.isOwnerOfBusiness(#businessId, authentication.details.subject)")
    public StaffInvitation sendInvitation(@PathVariable("subject") String sub, @PathVariable("businessId") long businessId) {
        return staffManagement.sendInvitation(
                new StaffInvitationCommand(
                        sub,
                        businessId
                )
        );
    }

    @PostMapping("/send-invitation/{invitationId}/{accept}")
    @PreAuthorize("isAuthenticated() && @authorizationHandler.isInvitedStaff(#invitationId, authentication.details.subject)")
    public void sendInvitation(@PathVariable("invitationId") long invitationId, @PathVariable("accept") boolean accept) {
        staffManagement.answerInvitation(
                new StaffAnswerInvitationCommand(
                        invitationId,
                        accept
                )
        );
    }

    @PutMapping("/update-details")
    @PreAuthorize("isAuthenticated() && @authorizationHandler.isStaff(#command.staffId(), authentication.details.subject)")
    public void updateDetails(@RequestBody StaffChangeDetailsCommand command) {
        staffManagement.changeDetails(command);
    }
}
