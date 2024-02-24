package net.kravuar.staff.ports.out;

import jakarta.validation.Valid;
import net.kravuar.context.AppValidated;
import net.kravuar.staff.domain.Staff;

@AppValidated
public interface StaffPersistencePort {
    /**
     * Save staff member.
     *
     * @param staff Staff entity to save
     * @return saved {@link Staff} object
     */
    Staff saveStaff(@Valid Staff staff);
}
