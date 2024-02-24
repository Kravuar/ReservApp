package net.kravuar.staff.ports.out;

import net.kravuar.staff.domain.StaffInvitation;
import net.kravuar.staff.domain.exceptions.InvitationNotFoundException;

import java.util.List;

public interface InvitationRetrievalPort {
    /**
     * Find staff invitation by id.
     *
     * @param invitationId ID of the invitation to find
     * @return {@link StaffInvitation} staff invitation associated with the provided sub and business ID
     * @throws InvitationNotFoundException if invitation wasn't found
     */
    StaffInvitation findStaffInvitationByBusiness(long invitationId);

    /**
     * Check whether invitation exists.
     *
     * @param sub subject of the staff invitations
     * @param businessId ID of the business
     * @return {@code true} if exists, {@code false} otherwise
     */
    boolean existsByBusiness(String sub, long businessId);

    /**
     * Find staff invitations by subject.
     *
     * @param sub subject of the staff invitations
     * @return {@link List<StaffInvitation>} staff invitations associated with the provided subject
     */
    List<StaffInvitation> findStaffInvitationBySubject(String sub);

    /**
     * Find staff invitations by business.
     *
     * @param businessId ID of the business
     * @return {@link List<StaffInvitation>} staff invitations associated with the provided business ID
     */
    List<StaffInvitation> findStaffInvitationsByBusiness(long businessId);
}
