package net.kravuar.staff.ports.in;

import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.domain.StaffInvitation;
import net.kravuar.staff.domain.exceptions.InvitationNotFoundException;
import net.kravuar.staff.domain.exceptions.StaffNotFoundException;

import java.util.List;

public interface StaffRetrievalUseCase {
    /**
     * Find staff member by ID.
     *
     * @param staffId    id of the staff member to find
     * @param activeOnly whether to search active only
     * @return {@link Staff} object associated with the provided {@code staffId}
     * @throws StaffNotFoundException if staff member wasn't found
     */
    Staff findStaffById(long staffId, boolean activeOnly);

    /**
     * Find all staff members by business.
     *
     * @param businessId id of the business
     * @param activeOnly whether to search active only
     * @return {@link List<Staff>} of all staff members associated with the provided {@code businessId}
     */
    List<Staff> findAllStaffByBusiness(long businessId, boolean activeOnly);

    /**
     * Find staff invitation by id.
     *
     * @param invitationId id of the staff invitation
     * @return found {@link StaffInvitation}
     * @throws InvitationNotFoundException if invitation wasn't found
     */
    StaffInvitation findStaffInvitationById(long invitationId);

    /**
     * Find staff invitations by subject.
     *
     * @param sub subject of the staff invitations
     * @return {@link List<StaffInvitation>} staff invitations associated with the provided {@code subject}
     */
    List<StaffInvitation> findStaffInvitationsBySubject(String sub);

    /**
     * Find staff invitations by business.
     *
     * @param businessId id of the business
     * @return {@link List<StaffInvitation>} staff invitations associated with the provided {@code businessId}
     */
    List<StaffInvitation> findStaffInvitationsByBusiness(long businessId);
}