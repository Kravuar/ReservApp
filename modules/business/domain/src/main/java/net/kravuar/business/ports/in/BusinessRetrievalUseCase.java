package net.kravuar.business.ports.in;

import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.exceptions.BusinessNotFoundException;

public interface BusinessRetrievalUseCase {
    /**
     * Find business by business ID.
     *
     * @param id the id of the business to find
     * @return the business associated with the provided ID
     * @throws BusinessNotFoundException if the business wasn't found
     */
    Business findById(long id);
}