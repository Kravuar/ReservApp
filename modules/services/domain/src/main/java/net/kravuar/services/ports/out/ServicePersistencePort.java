package net.kravuar.services.ports.out;

import jakarta.validation.Valid;
import net.kravuar.context.AppValidated;
import net.kravuar.services.domain.Service;
import net.kravuar.services.domain.exceptions.BusinessNotFoundException;
import net.kravuar.services.domain.exceptions.ServiceNameAlreadyTaken;

@AppValidated
public interface ServicePersistencePort {
    /**
     * Save service.
     *
     * @param service service to save
     * @return saved {@link Service} object
     * @throws BusinessNotFoundException if the business to associate with wasn't found
     * @throws ServiceNameAlreadyTaken if service name already taken
     */
    Service save(@Valid Service service);
}