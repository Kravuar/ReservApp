package net.kravuar.services.ports.in;

import net.kravuar.services.domain.Service;
import net.kravuar.services.domain.exceptions.ServiceNotFoundException;

import java.util.List;

public interface ServiceRetrievalUseCase {
    /**
     * Find service by serviceId.
     *
     * @param id id of the service to find
     * @return {@link Service} associated the with provided serviceId
     * @throws ServiceNotFoundException if service wasn't found
     */
    Service findById(long id);

    /**
     * Find all active services by associated business.
     *
     * @param businessId id of the associated business
     * @return {@link List<Service>} of active services associated the with provided {@code businessId}
     */
    List<Service> findAllActiveByBusiness(long businessId);

    /**
     * Find all active services.
     *
     * @return {@link List<Service>} of active services.
     */
    List<Service> findAllActive();
}