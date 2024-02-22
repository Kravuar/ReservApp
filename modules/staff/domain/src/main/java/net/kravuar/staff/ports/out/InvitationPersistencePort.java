package net.kravuar.staff.ports.out;

import net.kravuar.staff.domain.StaffInvitation;

public interface InvitationPersistencePort {
    /**
     * Save staff invitation.
     *
     * @param invitation StaffInvitation entity to save
     * @return saved {@link StaffInvitation} object
     */
    StaffInvitation saveStaffInvitation(StaffInvitation invitation);
}
