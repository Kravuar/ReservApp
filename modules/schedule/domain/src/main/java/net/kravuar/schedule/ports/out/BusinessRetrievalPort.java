package net.kravuar.schedule.ports.out;

import net.kravuar.schedule.domain.exceptions.BusinessNotFoundException;
import net.kravuar.staff.model.Business;

public interface BusinessRetrievalPort {
    /**
     * Find active business by id.
     *
     * @param businessId id of the business
     * @return {@link Business} associated the with provided {@code businessId}
     * @throws BusinessNotFoundException if business wasn't found
     */
    Business findActiveById(long businessId);
}
