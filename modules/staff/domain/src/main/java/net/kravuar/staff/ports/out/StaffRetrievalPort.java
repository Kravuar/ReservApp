package net.kravuar.staff.ports.out;

import net.kravuar.pageable.Page;
import net.kravuar.staff.domain.exceptions.StaffNotFoundException;
import net.kravuar.staff.model.Staff;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface StaffRetrievalPort {
    /**
     * Find staff member by id.
     * Only with active parent entities (business),
     * otherwise schedule exception day should not be visible.
     *
     * @param staffId    id of the staff member to find
     * @param activeOnly whether to search active only
     * @return {@link Staff} object associated with the provided {@code staffId}
     * @throws StaffNotFoundException if staff member wasn't found
     */
    Staff findById(long staffId, boolean activeOnly);

    /**
     * Find staff members by business with pageable.
     * Only with active parent entities (business),
     * otherwise schedule exception day should not be visible.
     *
     * @param page       page number
     * @param pageSize   size of the page
     * @param businessId id of the business
     * @param activeOnly whether to search active staff only
     * @return page of staff members associated with the provided {@code businessId}
     */
    Page<Staff> findByBusiness(long businessId, boolean activeOnly, int page, int pageSize);

    /**
     * Check whether an active staff exists in business.
     * Only with active parent entities (business),
     * otherwise schedule exception day should not be visible.
     *
     * @param businessId id of the business
     * @param sub        subject of the staff
     * @return {@code true} if exists, {@code false} otherwise
     */
    boolean existsActiveByBusinessAndSub(long businessId, String sub);

    /**
     * Find staff by business and sub.
     * Only with active parent entities (business),
     * otherwise schedule exception day should not be visible.
     *
     * @param businessId         id of the business
     * @param sub                subject of the staff
     * @param activeOnly         whether to search active staff only
     * @param activeBusinessOnly whether to search by active business only
     * @return {@code Optional<Staff>} object of staff associated with the provided {@code businessId} and {@code sub}
     */
    Optional<Staff> findByBusinessAndSub(long businessId, String sub, boolean activeOnly, boolean activeBusinessOnly);

    /**
     * Find staff by ids.
     *
     * @param staffIds   ids of the staff to find
     * @param activeOnly whether to search active only
     * @return list of {@link Staff} entities associated the with provided ids
     */
    List<Staff> findByIds(Set<Long> staffIds, boolean activeOnly);
}
