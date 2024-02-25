package net.kravuar.staff.ports.in;

import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.domain.StaffInvitation;
import net.kravuar.staff.domain.exceptions.StaffNotFoundException;

import java.util.List;

public interface StaffRetrievalUseCase {
    /**
     * Find staff member by ID.
     *
     * @param id ID of the staff member to find
     * @return {@link Staff} object associated with the provided ID
     * @throws StaffNotFoundException if staff member wasn't found
     */
    Staff findStaffById(long id);

    /**
     * Find all staff members by business.
     *
     * @param businessId ID of the business
     * @return {@link List<Staff>} all staff members associated with the provided business ID
     */
    List<Staff> findAllStaffByBusiness(long businessId);

    /**
     * Find staff invitations by subject.
     *
     * @param sub subject of the staff invitations
     * @return {@link List< StaffInvitation >} staff invitations associated with the provided subject
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