package net.kravuar.services.ports.in;

import jakarta.validation.Valid;
import net.kravuar.services.domain.Business;
import net.kravuar.services.domain.commands.BusinessChangeActiveCommand;
import net.kravuar.services.domain.commands.BusinessCreationCommand;
import net.kravuar.services.domain.exceptions.BusinessNotFoundException;

public interface BusinessManagementUseCase {
    /**
     * Creates a {@link Business}.
     *
     * @param command the command containing information for business creation
     * @return Newly created {@link Business}
     */
    Business create(@Valid BusinessCreationCommand command);

    /**
     * Enables/disables a {@link Business}.
     *
     * @param command the command containing information for enabling/disabling business
     * @throws BusinessNotFoundException if business wasn't found
     */
    void changeActive(@Valid BusinessChangeActiveCommand command);
}