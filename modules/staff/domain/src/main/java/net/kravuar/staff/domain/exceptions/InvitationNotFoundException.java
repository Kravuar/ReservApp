package net.kravuar.staff.domain.exceptions;

public class InvitationNotFoundException extends RuntimeException {
    public InvitationNotFoundException() {
        super("Invitation not found");
    }
}
