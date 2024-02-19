package net.kravuar.business.ports.in;

import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.exceptions.BusinessNotFoundException;

import java.util.List;

public interface BusinessRetrievalUseCase {
    /**
     * Find business by business ID.
     *
     * @param id the id of the business to find
     * @return the business associated with the provided ID
     * @throws BusinessNotFoundException if the business wasn't found
     */
    Business findById(long id);

    /**
     * Find business by owner sub.
     *
     * @param sub sub of the owner
     * @return the business associated with the provided sub
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