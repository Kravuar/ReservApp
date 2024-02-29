package net.kravuar.staff.ports.out;

import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.domain.exceptions.StaffNotFoundException;

import java.util.List;
import java.util.Optional;

public interface StaffRetrievalPort {
    /**
     * Find staff member by ID.
     *
     * @param staffId    id of the staff member to find
     * @param activeOnly whether to search active only
     * @return {@link Staff} object associated with the provided {@code staffId}
     * @throws StaffNotFoundException if staff member wasn't found
     */
    Staff findById(long staffId, boolean activeOnly);

    /**
     * Find all staff members by business.
     *
     * @param businessId id of the business
     * @param activeOnly whether to search active only
     * @return {@code List<Staff>} all staff members associated with the provided {@code businessId}
     */
    List<Staff> findAllStaffByBusinessId(long businessId, boolean activeOnly);

    /**
     * Check whether an active staff exists.
     *
     * @param businessId id of the business
     * @param sub        subject of the staff
     * @return {@code true} if exists, {@code false} otherwise
     */
    boolean existsActiveByBusinessIdAndSub(long businessId, String sub);

    /**
     * Find staff by business and sub.
     *
     * @param businessId id of the business
     * @param sub        subject of the staff
     * @param activeOnly whether to search active only
     * @return {@code Optional<Staff>} object of staff associated with the provided {@code businessId} and {@code sub}
     */
    Optional<Staff> findByBusinessIdAndSub(long businessId, String sub, boolean activeOnly);
}
