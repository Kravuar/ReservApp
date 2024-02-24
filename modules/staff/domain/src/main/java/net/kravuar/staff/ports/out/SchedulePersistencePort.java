package net.kravuar.staff.ports.out;

import jakarta.validation.Valid;
import net.kravuar.context.AppValidated;
import net.kravuar.staff.domain.DailySchedule;

@AppValidated
public interface SchedulePersistencePort {
    /**
     * Save schedule change.
     *
     * @param schedule new schedule object to save
     */
    void saveStaffScheduleChange(@Valid DailySchedule schedule);
}
