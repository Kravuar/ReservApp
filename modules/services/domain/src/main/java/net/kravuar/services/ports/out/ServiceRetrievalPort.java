package net.kravuar.services.ports.out;

import net.kravuar.services.domain.Service;
import net.kravuar.services.domain.exceptions.ServiceNotFoundException;

import java.util.List;

public interface ServiceRetrievalPort {
    /**
     * Find active service by serviceId.
     * Only with active parent entities (business),
     * otherwise schedule exception day should not be visible.
     *
     * @param serviceId  id of the service to find
     * @param activeOnly whether to search active service only
     * @return {@link Service} associated the with provided {@code serviceId}
     * @throws ServiceNotFoundException if service wasn't found
     */
    Service findById(long serviceId, boolean activeOnly);

    /**
     * Find all services by associated active business.
     *
     * @param businessId id of the business
     * @param activeOnly whether to search active services only
     * @return {@code List<Service>} of services associated the with provided {@code businessId}
     */
    List<Service> findAllByActiveBusinessId(long businessId, boolean activeOnly);

    /**
     * Find all active services.
     *
     * @return {@code List<Service>} of active services.
     */
    List<Service> findAllActive();
}
