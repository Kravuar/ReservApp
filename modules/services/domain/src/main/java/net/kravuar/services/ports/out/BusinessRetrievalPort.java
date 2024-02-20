package net.kravuar.services.ports.out;

import net.kravuar.services.domain.Business;
import net.kravuar.services.domain.exceptions.BusinessNotFoundException;

public interface BusinessRetrievalPort {
    /**
     * Find business by businessId.
     *
     * @param id businessId of the business to find
     * @return {@link Business} associated the with provided businessId
     * @throws BusinessNotFoundException if business wasn't found
     */
    Business findById(long id);
}
