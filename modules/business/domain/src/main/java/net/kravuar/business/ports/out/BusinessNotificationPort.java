package net.kravuar.business.ports.out;

import net.kravuar.business.domain.Business;

public interface BusinessNotificationPort {
    /**
     * Notify about new business.
     */
    void notifyNewBusiness(Business business);

    /**
     * Notify about business enabled/disabled.
     */
    void notifyBusinessActiveChanged(Business business);
}