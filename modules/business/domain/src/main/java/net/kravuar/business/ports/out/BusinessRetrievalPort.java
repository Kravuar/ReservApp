package net.kravuar.business.ports.out;

import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.exceptions.BusinessNotFoundException;

import java.util.List;

public interface BusinessRetrievalPort {
    /**
     * Find business by businessId.
     *
     * @param businessId id of the business to find
     * @param activeOnly whether to search active only
     * @return {@link Business} associated the with provided {@code businessId}
     * @throws BusinessNotFoundException if business wasn't found
     */
    Business findById(long businessId, boolean activeOnly);

    /**
     * Check whether an active business exists by name.
     *
     * @param name name to check
     * @return {@code true} if exists, {@code false} otherwise
     */
    boolean existsActiveByName(String name);

    /**
     * Find all businesses by owner.
     *
     * @param sub        sub of the owner
     * @param activeOnly whether to search active only
     * @return {@code List<Business>} of businesses associated the with provided owner {@code sub}
     */
    List<Business> findBySub(String sub, boolean activeOnly);

    /**
     * Find all active.
     *
     * @return all existing and active businesses
     */
    List<Business> findAllActive();
}