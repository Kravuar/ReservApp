package net.kravuar.schedule.ports.out;

import net.kravuar.staff.model.Schedule;
import net.kravuar.staff.model.ScheduleExceptionDay;

public interface SchedulePersistencePort {
    /**
     * Save schedule.
     *
     * @param schedule new schedule object to save
     */
    Schedule save(Schedule schedule);

    /**
     * Save schedule exceptionDay.
     *
     * @param scheduleExceptionDay new schedule object to save
     */
    ScheduleExceptionDay save(ScheduleExceptionDay scheduleExceptionDay);
}
