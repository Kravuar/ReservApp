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
     * Find business by email.
     *
     * @param email email of the business to find
     * @return {@link Business} associated the with provided email
     * @throws BusinessNotFoundException if business wasn't found
     */
    Business findByEmail(String email);

    /**
     * Check whether business exists by email.
     *
     * @param email the email to check
     * @return {@code true} if exists, {@code false} otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Save business.
     *
     * @param business business to save
     * @return saved {@link Business} object
     */
    Business save(Business business);
}
