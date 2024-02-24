package net.kravuar.business.ports.out;

import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.exceptions.BusinessNotFoundException;

import java.util.List;

public interface BusinessRetrievalPort {
    /**
     * Find business by businessId.
     *
     * @param id businessId of the business to find
     * @return {@link Business} associated the with provided businessId
     * @throws BusinessNotFoundException if business wasn't found
     */
    Business findById(long id);

    /**
     * Check whether business exists by name.
     *
     * @param name name to check
     * @return {@code true} if exists, {@code false} otherwise
     */
    boolean existsByName(String name);

    /**
     * Find active businesses by owner.
     *
     * @param sub sub of the owner
     * @return {@link List<Business>} active businesses associated the with provided owner
     */
    List<Business> findActiveBySub(String sub);

    /**
     * Find all active.
     *
     * @return all existing and active businesses
     */
    List<Business> findAllActive();
}