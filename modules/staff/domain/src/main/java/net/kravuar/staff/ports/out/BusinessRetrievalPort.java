package net.kravuar.staff.ports.out;

import net.kravuar.staff.domain.Business;
import net.kravuar.staff.domain.exceptions.BusinessNotFoundException;

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
