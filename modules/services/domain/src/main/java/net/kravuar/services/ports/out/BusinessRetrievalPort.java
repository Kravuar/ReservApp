package net.kravuar.services.ports.out;

import net.kravuar.services.domain.exceptions.BusinessNotFoundException;
import net.kravuar.services.model.Business;

public interface BusinessRetrievalPort {
    /**
     * Find active business by id.
     *
     * @param businessId id of the business to find
     * @return {@link Business} associated the with provided {@code businessId}
     * @throws BusinessNotFoundException if business wasn't found
     */
    Business findActiveById(long businessId);
}
