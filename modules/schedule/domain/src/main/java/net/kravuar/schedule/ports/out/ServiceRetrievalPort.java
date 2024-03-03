package net.kravuar.schedule.ports.out;

import net.kravuar.schedule.domain.Service;
import net.kravuar.schedule.domain.exceptions.ServiceNotFoundException;

public interface ServiceRetrievalPort {
    /**
     * Find active service by id.
     * Only with active parent entities (business),
     * otherwise schedule exception day should not be visible.
     *
     * @param serviceId id of the service to find
     * @return {@link Service} associated the with provided {@code serviceId}
     * @throws ServiceNotFoundException if service wasn't found
     */
    Service findActiveById(long serviceId);
}