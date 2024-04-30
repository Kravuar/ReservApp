package net.kravuar.staff.domain.exceptions;

import net.kravuar.staff.model.StaffInvitation;

public class InvitationInvalidStatusException extends StaffException {
    public InvitationInvalidStatusException(StaffInvitation.Status status) {
        super("Invalid invitation status: " + status);
    }
}
