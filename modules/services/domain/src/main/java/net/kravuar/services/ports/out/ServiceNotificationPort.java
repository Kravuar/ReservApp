package net.kravuar.services.ports.out;


import net.kravuar.services.model.Service;

public interface ServiceNotificationPort {
    /**
     * Notify about new service.
     */
    void notifyNewService(Service service);

    /**
     * Notify about service active status change (enabled/disabled).
     */
    void notifyServiceActiveChanged(Service service);
}