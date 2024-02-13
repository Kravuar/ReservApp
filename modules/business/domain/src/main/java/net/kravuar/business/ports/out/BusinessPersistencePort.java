package net.kravuar.business.ports.out;

import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.exceptions.BusinessNotFoundException;

public interface BusinessPersistencePort {
    /**
     * Find business by businessId.
     *
     * @param id businessId of the business to find
     * @return {@link Business} associated the with provided businessId
     * @throws BusinessNotFoundException if business wasn't found
     */
    Business findById(long id);

    /**
     * Checks whether business with provided name exists.
     *
     * @param name name of the business to check
     * @return {@code true} if exists, {@code false} otherwise
     */
    boolean existsByName(String name);

    /**
     * Save business.
     *
     * @param business business to save
     * @return saved {@link Business} object
     */
    Business save(Business business);
}
