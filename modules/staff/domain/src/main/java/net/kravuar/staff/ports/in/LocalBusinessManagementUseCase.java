package net.kravuar.staff.ports.in;

import net.kravuar.staff.domain.Business;
import net.kravuar.staff.domain.commands.BusinessChangeActiveCommand;
import net.kravuar.staff.domain.commands.BusinessCreationCommand;
import net.kravuar.staff.domain.exceptions.BusinessNotFoundException;

public interface LocalBusinessManagementUseCase {
    /**
     * Creates a {@link Business}.
     *
     * @param command the command containing information for business creation
     * @return Newly created {@link Business}
     */
    Business create(BusinessCreationCommand command);

    /**
     * Enables/disables a {@link Business}.
     *
     * @param command the command containing information for enabling/disabling business
     * @throws BusinessNotFoundException if business wasn't found
     */
    void changeActive(BusinessChangeActiveCommand command);
}