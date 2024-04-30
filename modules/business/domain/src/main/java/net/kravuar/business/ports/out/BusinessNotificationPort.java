package net.kravuar.business.ports.out;

import net.kravuar.business.model.Business;

public interface BusinessNotificationPort {
    /**
     * Notify about new business.
     */
    void notifyNewBusiness(Business business);

    /**
     * Notify about business active status change (enabled/disabled).
     */
    void notifyBusinessActiveChanged(Business business);
}