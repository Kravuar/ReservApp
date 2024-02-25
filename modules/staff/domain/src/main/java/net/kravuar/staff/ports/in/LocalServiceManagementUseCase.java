package net.kravuar.staff.ports.in;

import net.kravuar.staff.domain.Service;
import net.kravuar.staff.domain.commands.ServiceChangeActiveCommand;
import net.kravuar.staff.domain.commands.ServiceCreationCommand;
import net.kravuar.staff.domain.exceptions.BusinessNotFoundException;
import net.kravuar.staff.domain.exceptions.ServiceNotFoundException;

public interface LocalServiceManagementUseCase {
    /**
     * Creates a {@link Service}.
     *
     * @param command the command containing information for service creation
     * @return Newly created {@link Service}
     * @throws BusinessNotFoundException if business to associate service with wasn't found
     */
    Service create(ServiceCreationCommand command);

    /**
     * Enables/disables a {@link Service}.
     *
     * @param command the command containing information for enabling/disabling service
     * @throws ServiceNotFoundException if service wasn't found
     */
    void changeActive(ServiceChangeActiveCommand command);
}