package net.kravuar.services.ports.out;

import net.kravuar.services.domain.Service;
import net.kravuar.services.domain.exceptions.ServiceNotFoundException;

import java.util.List;

public interface ServiceRetrievalPort {
    /**
     * Find active service by serviceId.
     *
     * @param serviceId  id of the service to find
     * @param activeOnly whether to search active only
     * @return {@link Service} associated the with provided {@code serviceId}
     * @throws ServiceNotFoundException if service wasn't found
     */
    Service findById(long serviceId, boolean activeOnly);

    /**
     * Check whether an active service exists by name.
     *
     * @param name name to check
     * @return {@code true} if exists, {@code false} otherwise
     */
    boolean existsActiveByName(String name);

    /**
     * Find all services by associated business.
     *
     * @param businessId id of the business
     * @param activeOnly whether to search active only
     * @return {@link List<Service>} of services associated the with provided {@code businessId}
     */
    List<Service> findAllByBusinessId(long businessId, boolean activeOnly);

    /**
     * Find all active services.
     *
     * @return {@link List<Service>} of active services.
     */
    List<Service> findAllActive();
}
