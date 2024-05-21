package net.kravuar.staff.ports.out;

import net.kravuar.pageable.Page;
import net.kravuar.staff.model.StaffInvitation;
import net.kravuar.staff.domain.exceptions.InvitationNotFoundException;

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
     * Find staff invitations by subject with pageable.
     * Only with active parent entities (business),
     * otherwise schedule exception day should not be visible.
     *
     * @param page     page number
     * @param pageSize size of the page
     * @param sub      subject of the staff invitations
     * @return page of staff invitations associated with the provided {@code subject}
     */
    Page<StaffInvitation> findBySubject(String sub, int page, int pageSize);

    /**
     * Find staff invitations by business with pageable.
     * Only with active parent entities (business),
     * otherwise schedule exception day should not be visible.
     *
     * @param page       page number
     * @param pageSize   size of the page
     * @param businessId id of the business
     * @return page of staff invitations associated with the provided {@code businessId}
     */
    Page<StaffInvitation> findByBusiness(long businessId, int page, int pageSize);
}
