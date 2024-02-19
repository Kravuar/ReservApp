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
     * Find business by owner sub.
     *
     * @param sub sub of the owner
     * @return {@link Business} associated the with provided businessId
     * @throws BusinessNotFoundException if the business wasn't found
     */
    Business findBySub(String sub);

    /**
     * Find all active.
     *
     * @return all existing and active businesses
     */
    List<Business> findAllActive();
}
