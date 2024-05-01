package net.kravuar.schedule.ports.in;

import net.kravuar.schedule.domain.exceptions.BusinessNotFoundException;
import net.kravuar.schedule.model.Business;

public interface BusinessRetrievalUseCase {
    /**
     * Find business by id.
     *
     * @param businessId id of the business to find
     * @return {@link Business} associated the with provided {@code businessId}
     * @throws BusinessNotFoundException if business wasn't found
     */
    Business findById(long businessId);
}