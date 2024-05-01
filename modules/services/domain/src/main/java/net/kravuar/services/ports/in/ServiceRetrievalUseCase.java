package net.kravuar.services.ports.in;

import net.kravuar.pageable.Page;
import net.kravuar.services.domain.exceptions.ServiceNotFoundException;
import net.kravuar.services.model.Service;

public interface ServiceRetrievalUseCase {
    /**
     * Find service by serviceId.
     *
     * @param serviceId  id of the service to find
     * @param activeOnly whether to search active only
     * @return {@link Service} associated the with provided {@code serviceId}
     * @throws ServiceNotFoundException if service wasn't found
     */
    Service findById(long serviceId, boolean activeOnly);

    /**
     * Find active services by associated business with pageable.
     *
     * @param page page number
     * @param pageSize size of the page
     * @param businessId id of the associated business
     * @param activeOnly whether to search active only
     * @return page of active services associated the with provided {@code businessId}
     */
    Page<Service> findActiveByActiveBusinessId(long businessId, boolean activeOnly, int page, int pageSize);

    /**
     * Find active services with pageable.
     *
     * @param page page number
     * @param pageSize size of the page
     * @return page of active services.
     */
    Page<Service> findActive(int page, int pageSize);

    /**
     * Find service by id.
     * Will return inactive if sub matches with owner's one.
     *
     * @param serviceId         id of the service to find
     * @param sub        sub of the owner (nullable)
     * @return {@link Service} associated the with provided serviceId
     * @throws ServiceNotFoundException if service wasn't found
     */
    Service findByIdAndSub(long serviceId, String sub);
}