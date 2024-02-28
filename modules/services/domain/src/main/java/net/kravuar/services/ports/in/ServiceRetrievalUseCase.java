package net.kravuar.services.ports.in;

import net.kravuar.services.domain.Service;
import net.kravuar.services.domain.exceptions.ServiceNotFoundException;

import java.util.List;

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
     * Find all active services by associated business.
     *
     * @param businessId id of the associated business
     * @param activeOnly whether to search active only
     * @return {@link List<Service>} of active services associated the with provided {@code businessId}
     */
    List<Service> findAllByBusinessId(long businessId, boolean activeOnly);

    /**
     * Find all active services.
     *
     * @return {@link List<Service>} of active services.
     */
    List<Service> findAll();
}