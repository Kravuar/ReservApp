package net.kravuar.staff.notification;

import net.kravuar.context.AppComponent;
import net.kravuar.staff.domain.DailySchedule;
import net.kravuar.staff.ports.out.ScheduleNotificationPort;

@AppComponent
class NoopScheduleNotificationAdapter implements ScheduleNotificationPort {

    @Override
    public void notifyScheduleChange(DailySchedule schedule) {
        System.out.println("SCHEDULE CHANGE " + schedule.getId() + " | " + schedule.getDayOfWeek() + " | " + schedule.getValidFrom());
    }
}
