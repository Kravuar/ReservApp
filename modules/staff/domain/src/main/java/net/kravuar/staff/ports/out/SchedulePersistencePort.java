package net.kravuar.staff.ports.out;

import net.kravuar.staff.domain.StaffSchedule;

public interface SchedulePersistencePort {
    /**
     * Save schedule change.
     *
     * @param schedule new schedule object to save
     * @return saved {@link StaffSchedule} object
     */
    StaffSchedule saveStaffInvitation(StaffSchedule schedule);
}
