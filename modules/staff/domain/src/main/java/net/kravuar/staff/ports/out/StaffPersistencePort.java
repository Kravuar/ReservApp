package net.kravuar.staff.ports.out;

import net.kravuar.staff.domain.Staff;

public interface StaffPersistencePort {
    /**
     * Save staff member.
     *
     * @param staff Staff entity to save
     * @return saved {@link Staff} object
     */
    Staff saveStaff(Staff staff);
}
