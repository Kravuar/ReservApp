package net.kravuar.staff.ports.out;

import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.domain.exceptions.StaffNotFoundException;

import java.util.List;

public interface StaffRetrievalPort {
    /**
     * Find staff member by ID.
     *
     * @param staffId id of the staff member to find
     * @return {@link Staff} object associated with the provided {@code staffId}
     * @throws StaffNotFoundException if staff member wasn't found
     */
    Staff findById(long staffId);

    /**
     * Find active staff member by ID.
     *
     * @param staffId id of the staff member to find
     * @return active {@link Staff} object associated with the provided {@code staffId}
     * @throws StaffNotFoundException if staff member wasn't found
     */
    Staff findActiveById(long staffId);

    /**
     * Find all staff members by business.
     *
     * @param businessId id of the business
     * @return {@link List<Staff>} all staff members associated with the provided {@code businessId}
     */
    List<Staff> findAllStaffByBusinessId(long businessId);

    /**
     * Check whether an active staff exists.
     *
     * @param businessId id of the business
     * @param sub        subject of the staff
     * @return {@code true} if exists, {@code false} otherwise
     */
    boolean existsActiveByBusinessIdAndSub(long businessId, String sub);
}
