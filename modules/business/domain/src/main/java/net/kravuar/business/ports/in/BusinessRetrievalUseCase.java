package net.kravuar.business.ports.in;

import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.exceptions.BusinessNotFoundException;

import java.util.List;

public interface BusinessRetrievalUseCase {
    /**
     * Find business by businessId.
     *
     * @param id         id of the business to find
     * @param activeOnly whether to search active only
     * @return {@link Business} associated the with provided businessId
     * @throws BusinessNotFoundException if business wasn't found
     */
    Business findById(long id, boolean activeOnly);

    /**
     * Find businesses by owner sub.
     *
     * @param sub        sub of the owner
     * @param activeOnly whether to search active only
     * @return {@link List<Business>} of businesses associated the with provided owner {@code sub}
     */
    List<Business> findAllBySub(String sub, boolean activeOnly);

    /**
     * Find all active businesses.
     *
     * @return all existing active businesses
     */
    List<Business> findAllActive();
}