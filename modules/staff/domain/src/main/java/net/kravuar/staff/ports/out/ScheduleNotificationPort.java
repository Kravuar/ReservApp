package net.kravuar.staff.ports.out;

import jakarta.validation.Valid;
import net.kravuar.staff.domain.DailySchedule;

public interface ScheduleNotificationPort {
    /**
     * Notify schedule change
     *
     * @param schedule new schedule
     */
    void notifyScheduleChange(@Valid DailySchedule schedule);
}
