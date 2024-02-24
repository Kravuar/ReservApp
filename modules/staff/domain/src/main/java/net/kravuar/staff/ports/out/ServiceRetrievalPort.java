package net.kravuar.staff.ports.out;

import net.kravuar.staff.domain.Service;
import net.kravuar.staff.domain.exceptions.ServiceNotFoundException;

public interface ServiceRetrievalPort {
    /**
     * Find service by serviceId.
     *
     * @param id serviceId of the service to find
     * @return {@link Service} associated the with provided serviceId
     * @throws ServiceNotFoundException if service wasn't found
     */
    Service findById(long id);
}
