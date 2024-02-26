package net.kravuar.staff.ports.out;

import jakarta.validation.Valid;
import net.kravuar.staff.domain.Staff;

public interface StaffNotificationPort {
    /**
     * Notify about new staff member.
     *
     * @param staff New staff member
     */
    void notifyNewStaff(@Valid Staff staff);

    /**
     * Notify about staff member's active status change (enabled/disabled).
     *
     * @param staff Staff member whose active status has changed
     */
    void notifyStaffActiveChanged(@Valid Staff staff);
}
