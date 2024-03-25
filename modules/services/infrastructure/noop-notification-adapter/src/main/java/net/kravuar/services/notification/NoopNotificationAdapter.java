package net.kravuar.services.notification;

import net.kravuar.context.AppComponent;
import net.kravuar.services.domain.Service;
import net.kravuar.services.ports.out.ServiceNotificationPort;

@AppComponent
class NoopNotificationAdapter implements ServiceNotificationPort {

    @Override
    public void notifyNewService(Service service) {
        System.out.println("CREATE " + service.getId());
    }

    @Override
    public void notifyServiceActiveChanged(Service service) {
        System.out.println("ACTIVITY CHANGE " + service.getId() + " | " + service.isActive());
    }
}
