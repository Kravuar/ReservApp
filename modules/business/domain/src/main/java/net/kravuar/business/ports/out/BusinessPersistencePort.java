package net.kravuar.business.ports.out;

import net.kravuar.business.domain.Business;

public interface BusinessPersistencePort {
    /**
     * Save business.
     *
     * @param business business to save
     * @return saved {@link Business} object
     */
    Business save(Business business);
}
