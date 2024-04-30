package net.kravuar.business.notification;

import net.kravuar.business.model.Business;
import net.kravuar.business.ports.out.BusinessNotificationPort;
import net.kravuar.context.AppComponent;

@AppComponent
class NoopNotificationAdapter implements BusinessNotificationPort {

    @Override
    public void notifyNewBusiness(Business business) {
        System.out.println("CREATE " + business.getId());
    }

    @Override
    public void notifyBusinessActiveChanged(Business business) {
        System.out.println("ACTIVITY CHANGE " + business.getId() + " | " + business.isActive());
    }
}
