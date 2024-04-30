package net.kravuar.staff.ports.out;

import net.kravuar.staff.model.Business;
import net.kravuar.staff.domain.exceptions.BusinessNotFoundException;

public interface BusinessRetrievalPort {
    /**
     * Find active business by id.
     *
     * @param businessId id of the business to find
     * @return {@link Business} associated the with provided {@code businessId}
     * @throws BusinessNotFoundException if business wasn't found
     */
    Business findById(long businessId);
}
