package net.kravuar.business.ports.in;

import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.exceptions.BusinessNotFoundException;

import java.util.List;

public interface BusinessRetrievalUseCase {
    /**
     * Find business by businessId.
     *
     * @param id id of the business to find
     * @return {@link Business} associated the with provided businessId
     * @throws BusinessNotFoundException if business wasn't found
     */
    Business findById(long id);

    /**
     * Find active businesses by owner sub.
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