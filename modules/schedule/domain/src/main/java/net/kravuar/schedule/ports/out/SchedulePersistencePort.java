package net.kravuar.schedule.ports.out;

import net.kravuar.schedule.domain.Schedule;
import net.kravuar.schedule.domain.ScheduleExceptionDay;

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
