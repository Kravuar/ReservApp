package net.kravuar.services.ports.out;

import net.kravuar.services.domain.Business;

public interface BusinessPersistencePort {
    /**
     * Save business.
     *
     * @param business business to save
     * @return saved {@link Business} object
     */
    Business save(Business business);
}
