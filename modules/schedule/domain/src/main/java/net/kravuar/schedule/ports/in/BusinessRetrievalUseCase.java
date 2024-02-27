package net.kravuar.schedule.ports.in;

import net.kravuar.schedule.domain.Business;
import net.kravuar.schedule.domain.exceptions.BusinessNotFoundException;

public interface BusinessRetrievalUseCase {
    /**
     * Find business by businessId.
     *
     * @param id id of the business to find
     * @return {@link Business} associated the with provided businessId
     * @throws BusinessNotFoundException if business wasn't found
     */
    Business findById(long id);
}