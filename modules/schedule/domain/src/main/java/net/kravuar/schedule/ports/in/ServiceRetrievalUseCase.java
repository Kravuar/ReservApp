package net.kravuar.schedule.ports.in;

import net.kravuar.schedule.domain.Service;
import net.kravuar.schedule.domain.exceptions.ServiceNotFoundException;

public interface ServiceRetrievalUseCase {
    /**
     * Find service by id.
     *
     * @param id id of the service to find
     * @return {@link Service} associated the with provided {@code serviceId}
     * @throws ServiceNotFoundException if service wasn't found
     */
    Service findById(long id);
}