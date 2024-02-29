package net.kravuar.schedule.ports.out;

import net.kravuar.schedule.domain.Staff;
import net.kravuar.schedule.domain.exceptions.StaffNotFoundException;

public interface StaffRetrievalPort {
    /**
     * Find active staff by staff id.
     *
     * @param staffId id of the staff to find
     * @return active {@link Staff} associated the with provided {@code staffId}
     * @throws StaffNotFoundException if staff wasn't found
     */
    Staff findActiveById(long staffId);
}
