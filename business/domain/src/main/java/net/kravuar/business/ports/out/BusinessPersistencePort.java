package net.kravuar.business.ports.out;

import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.exceptions.BusinessNotFoundException;

public interface BusinessPersistencePort {
    /**
     * Find business by id.
     *
     * @param id id of the business to find
     * @return {@link Business} associated the with provided id
     * @throws BusinessNotFoundException if business wasn't found
     */
    Business findById(long id);

    /**
     * Save business.
     *
     * @param business business to save
     * @return saved {@link Business} object
     * @throws BusinessNotFoundException if business wasn't found
     */
    Business save(Business business);
}
