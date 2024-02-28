package net.kravuar.staff.ports.in;

import net.kravuar.staff.domain.Business;
import net.kravuar.staff.domain.exceptions.BusinessNotFoundException;

public interface BusinessRetrievalUseCase {
    /**
     * Find business by businessId.
     *
     * @param businessId id of the business to find
     * @param activeOnly whether to search active only
     * @return {@link Business} associated the with provided {@code businessId}
     * @throws BusinessNotFoundException if business wasn't found
     */
    Business findById(long businessId, boolean activeOnly);
}