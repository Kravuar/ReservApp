package net.kravuar.services.ports.in;

import jakarta.validation.Valid;
import net.kravuar.context.AppValidated;
import net.kravuar.services.domain.Service;
import net.kravuar.services.domain.commands.ServiceChangeActiveCommand;
import net.kravuar.services.domain.commands.ServiceChangeDetailsCommand;
import net.kravuar.services.domain.commands.ServiceCreationCommand;
import net.kravuar.services.domain.exceptions.BusinessNotFoundException;
import net.kravuar.services.domain.exceptions.ServiceNotFoundException;

@AppValidated
public interface ServiceManagementUseCase {
    /**
     * Creates a {@link Service}.
     *
     * @param command the command containing information for service creation
     * @return Newly created {@link Service}
     * @throws BusinessNotFoundException if an active business to associate service with wasn't found
     */
    Service create(@Valid ServiceCreationCommand command);

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