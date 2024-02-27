package net.kravuar.schedule.ports.out;

import net.kravuar.schedule.domain.Schedule;

public interface SchedulePersistencePort {
    /**
     * Save schedule change.
     *
     * @param schedule new schedule object to save
     */
    void save(Schedule schedule);
}
