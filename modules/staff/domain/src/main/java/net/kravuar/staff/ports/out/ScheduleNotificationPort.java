package net.kravuar.staff.ports.out;

import net.kravuar.staff.domain.DailySchedule;

public interface ScheduleNotificationPort {
    /**
     * Notify schedule change
     *
     * @param schedule new schedule
     */
    void notifyScheduleChange(DailySchedule schedule);
}
