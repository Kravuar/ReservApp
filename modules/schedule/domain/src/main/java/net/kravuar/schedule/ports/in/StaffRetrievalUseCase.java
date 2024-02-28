package net.kravuar.schedule.ports.in;

import net.kravuar.schedule.domain.Staff;
import net.kravuar.schedule.domain.exceptions.StaffNotFoundException;

public interface StaffRetrievalUseCase {
    /**
     * Find staff member by ID.
     *
     * @param staffId    id of the staff member to find
     * @param activeOnly whether to search active only
     * @return {@link Staff} object associated with the provided {@code staffId}
     * @throws StaffNotFoundException if staff member wasn't found
     */
    Staff findActiveById(long staffId, boolean activeOnly);
}