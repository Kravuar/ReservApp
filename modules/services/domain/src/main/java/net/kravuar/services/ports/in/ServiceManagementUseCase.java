package net.kravuar.services.ports.in;

import jakarta.validation.Valid;
import net.kravuar.context.AppValidated;
import net.kravuar.services.domain.Service;
import net.kravuar.services.domain.commands.ServiceChangeActiveCommand;
import net.kravuar.services.domain.commands.ServiceChangeDetailsCommand;
import net.kravuar.services.domain.commands.ServiceChangeNameCommand;
import net.kravuar.services.domain.commands.ServiceCreationCommand;
import net.kravuar.services.domain.exceptions.BusinessDisabledException;
import net.kravuar.services.domain.exceptions.BusinessNotFoundException;
import net.kravuar.services.domain.exceptions.ServiceNameAlreadyTaken;
import net.kravuar.services.domain.exceptions.ServiceNotFoundException;

@AppValidated
public interface ServiceManagementUseCase {
    /**
     * Creates a {@link Service}.
     *
     * @param command the command containing information for service creation
     * @return Newly created {@link Service}
     * @throws BusinessNotFoundException if business to associate service with wasn't found
     * @throws ServiceNameAlreadyTaken if service name already taken
     * @throws BusinessDisabledException if business is disabled
     */
    Service create(@Valid ServiceCreationCommand command);

    /**
     * Changes the name of a {@link Service}.
     *
     * @param command the command containing information for changing the service name
     * @throws ServiceNotFoundException if service wasn't found
     * @throws ServiceNameAlreadyTaken if service name already taken
     */
    void changeName(@Valid ServiceChangeNameCommand command);

    /**
     * Enables/disables a {@link Service}.
     *
     * @param command the command containing information for enabling/disabling service
     * @throws ServiceNotFoundException if service wasn't found
     */
    void changeActive(ServiceChangeActiveCommand command);

    /**
     * Changes details for a {@link Service}.
     *
     * @param command the command containing information for details update of the service
     * @throws ServiceNotFoundException if service wasn't found
     */
    void changeDetails(@Valid ServiceChangeDetailsCommand command);
}