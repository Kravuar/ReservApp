package net.kravuar.staff.domain.exceptions;

public class InvitationNotFoundException extends StaffException {
    public InvitationNotFoundException() {
        super("Invitation not found");
    }
}
