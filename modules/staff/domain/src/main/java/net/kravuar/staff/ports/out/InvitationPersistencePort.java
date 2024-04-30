package net.kravuar.staff.ports.out;

import net.kravuar.staff.model.StaffInvitation;

public interface InvitationPersistencePort {
    /**
     * Save staff invitation.
     *
     * @param invitation StaffInvitation entity to save
     * @return saved {@link StaffInvitation} object
     */
    StaffInvitation save(StaffInvitation invitation);
}
