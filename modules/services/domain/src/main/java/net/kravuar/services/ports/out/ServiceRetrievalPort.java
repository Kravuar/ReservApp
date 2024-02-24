package net.kravuar.services.ports.out;

import net.kravuar.services.domain.Service;
import net.kravuar.services.domain.exceptions.BusinessNotFoundException;
import net.kravuar.services.domain.exceptions.ServiceNotFoundException;

import java.util.List;

public interface ServiceRetrievalPort {
    /**
     * Find service by serviceId.
     *
     * @param id serviceId of the service to find
     * @return {@link Service} associated the with provided serviceId
     * @throws ServiceNotFoundException if service wasn't found
     */
    Service findById(long id);

    /**
     * Check whether service exists by name.
     *
     * @param name name to check
     * @return {@code true} if exists, {@code false} otherwise
     */
    boolean existsByName(String name);

    /**
     * Find all active services by associated business.
     *
     * @param businessId id of the associated business
     * @return {@link List<Service>} of active services associated the with provided {@code businessId}
     * @throws BusinessNotFoundException if the business wasn't found
     */
    List<Service> findAllActiveByBusiness(long businessId);

    /**
     * Find all services by associated business.
     *
     * @param businessId id of the associated business
     * @return {@link List<Service>} of services associated the with provided {@code businessId}
     * @throws BusinessNotFoundException if the business wasn't found
     */
    List<Service> findAllByBusiness(long businessId);

    /**
     * Find all active services.
     *
     * @return {@link List<Service>} of active services.
     */
    List<Service> findAllActive();
}
