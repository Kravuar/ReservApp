package net.kravuar.staff.notification;

import net.kravuar.context.AppComponent;
import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.ports.out.StaffNotificationPort;

@AppComponent
class NoopStaffNotificationAdapter implements StaffNotificationPort {
    @Override
    public void notifyNewStaff(Staff staff) {
        System.out.println("CREATE  " + staff.getId() + " | " + staff.getSub());
    }

    @Override
    public void notifyStaffActiveChanged(Staff staff) {
        System.out.println("ACTIVITY CHANGE " + staff.getId() + " | " + staff.isActive());
    }
}
