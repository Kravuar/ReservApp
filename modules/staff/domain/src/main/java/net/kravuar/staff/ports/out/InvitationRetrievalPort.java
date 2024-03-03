package net.kravuar.staff.ports.out;

import net.kravuar.staff.domain.StaffInvitation;
import net.kravuar.staff.domain.exceptions.InvitationNotFoundException;

import java.util.List;

public interface InvitationRetrievalPort {
    /**
     * Find staff invitation by id.
     * Only with active parent entities (business),
     * otherwise schedule exception day should not be visible.
     *
     * @param invitationId id of the invitation to find
     * @return {@link StaffInvitation} staff invitation associated with the provided {@code invitationId}
     * @throws InvitationNotFoundException if invitation wasn't found
     */
    StaffInvitation findById(long invitationId);

    /**
     * Check whether a waiting invitation exists.
     * Only with active parent entities (business),
     * otherwise schedule exception day should not be visible.
     *
     * @param businessId id of the business
     * @param sub        subject of the staff invitation
     * @return {@code true} if exists, {@code false} otherwise
     */
    boolean existsWaitingByBusinessAndSub(long businessId, String sub);

    /**
     * Find staff invitations by subject.
     * Only with active parent entities (business),
     * otherwise schedule exception day should not be visible.
     *
     * @param sub subject of the staff invitations
     * @return {@code List<StaffInvitation>} staff invitations associated with the provided {@code sub}
     */
    List<StaffInvitation> findAllBySubject(String sub);

    /**
     * Find staff invitations by active business.
     * Only with active parent entities (business),
     * otherwise schedule exception day should not be visible.
     *
     * @param businessId id the business
     * @return {@code List<StaffInvitation>} staff invitations associated with the provided {@code businessId}
     */
    List<StaffInvitation> findAllByBusiness(long businessId);
}
