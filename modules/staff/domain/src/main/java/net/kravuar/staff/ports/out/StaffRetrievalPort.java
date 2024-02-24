package net.kravuar.staff.ports.out;

import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.domain.exceptions.StaffNotFoundException;

import java.util.List;

public interface StaffRetrievalPort {
    /**
     * Find staff member by ID.
     *
     * @param id ID of the staff member to find
     * @return {@link Staff} object associated with the provided ID
     * @throws StaffNotFoundException if staff member wasn't found
     */
    Staff findStaffById(long id);

    /**
     * Check whether staff is currently working at
     *
     * @param serviceId ID of the staff member to find
     * @return {@code true} if has
     * @throws StaffNotFoundException if staff member wasn't found
     */
    boolean isWorkingAt(long serviceId);

    /**
     * Find all staff members by business.
     *
     * @param businessId ID of the business
     * @return {@link List<Staff>} all staff members associated with the provided business ID
     */
    List<Staff> findAllStaffByBusiness(long businessId);

    /**
     * Find all staff members by subject.
     *
     * @param sub subject of the staff members
     * @return {@link List<Staff>} all staff members associated with the provided subject
     */
    List<Staff> findAllStaffBySubject(String sub);
}
