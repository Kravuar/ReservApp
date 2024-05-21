package net.kravuar.staff.ports.in;

import net.kravuar.pageable.Page;
import net.kravuar.staff.model.StaffDetailed;
import net.kravuar.staff.model.StaffInvitation;
import net.kravuar.staff.domain.exceptions.InvitationNotFoundException;
import net.kravuar.staff.domain.exceptions.StaffNotFoundException;

public interface StaffRetrievalUseCase {
    /**
     * Find staff member with details by ID.
     *
     * @param staffId    id of the staff member to find
     * @param activeOnly whether to search active only
     * @return {@link StaffDetailed} object associated with the provided {@code staffId}
     * @throws StaffNotFoundException if staff member wasn't found
     */
    StaffDetailed findStaffById(long staffId, boolean activeOnly);

    /**
     * Find staff members with user info by business with pageable.
     *
     * @param page page number
     * @param pageSize size of the page
     * @param businessId id of the business
     * @param activeOnly whether to search active staff only
     * @return page of staff members with details associated with the provided {@code businessId}
     */
    Page<StaffDetailed> findStaffByBusiness(long businessId, boolean activeOnly, int page, int pageSize);

    /**
     * Find staff invitation by id.
     *
     * @param invitationId id of the staff invitation
     * @return found {@link StaffInvitation}
     * @throws InvitationNotFoundException if invitation wasn't found
     */
    StaffInvitation findStaffInvitationById(long invitationId);

    /**
     * Find staff invitations by subject with pageable.
     *
     * @param page page number
     * @param pageSize size of the page
     * @param sub subject of the staff invitations
     * @return page of staff invitations associated with the provided {@code subject}
     */
    Page<StaffInvitation> findStaffInvitationsBySubject(String sub, int page, int pageSize);

    /**
     * Find staff invitations by business with pageable.
     *
     * @param page page number
     * @param pageSize size of the page
     * @param businessId id of the business
     * @return page of staff invitations associated with the provided {@code businessId}
     */
    Page<StaffInvitation> findStaffInvitationsByBusiness(long businessId, int page, int pageSize);
}