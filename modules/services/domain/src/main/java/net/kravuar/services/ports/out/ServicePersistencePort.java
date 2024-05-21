package net.kravuar.services.ports.out;

import net.kravuar.services.domain.Service;

public interface ServicePersistencePort {
    /**
     * Save service.
     *
     * @param service service to save
     * @return saved {@link Service} object
     */
    Service save(Service service);
}