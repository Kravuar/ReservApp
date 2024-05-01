package net.kravuar.schedule.ports.in;

import net.kravuar.schedule.domain.exceptions.ServiceNotFoundException;
import net.kravuar.schedule.model.Service;

public interface ServiceRetrievalUseCase {
    /**
     * Find active service by id.
     *
     * @param id id of the service to find
     * @return {@link Service} associated the with provided {@code serviceId}
     * @throws ServiceNotFoundException if service wasn't found
     */
    Service findActiveById(long id);
}