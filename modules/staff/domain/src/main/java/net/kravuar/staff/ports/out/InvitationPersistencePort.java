package net.kravuar.staff.ports.out;

import jakarta.validation.Valid;
import net.kravuar.context.AppValidated;
import net.kravuar.staff.domain.StaffInvitation;

@AppValidated
public interface InvitationPersistencePort {
    /**
     * Save staff invitation.
     *
     * @param invitation StaffInvitation entity to save
     * @return saved {@link StaffInvitation} object
     */
    StaffInvitation saveStaffInvitation(@Valid StaffInvitation invitation);
}
