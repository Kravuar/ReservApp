package net.kravuar.staff.ports.out;

import net.kravuar.staff.domain.Business;
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
    Staff findById(long id);

    /**
     * Find all staff members by business.
     *
     * @param business the business
     * @return {@link List<Staff>} all staff members associated with the provided business ID
     */
    List<Staff> findAllStaffByBusiness(Business business);
}
