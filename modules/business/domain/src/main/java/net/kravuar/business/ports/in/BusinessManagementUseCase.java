package net.kravuar.business.ports.in;

import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.commands.BusinessChangeActiveCommand;
import net.kravuar.business.domain.commands.BusinessChangeNameCommand;
import net.kravuar.business.domain.commands.BusinessCreationCommand;
import net.kravuar.business.domain.exceptions.BusinessNameAlreadyTaken;
import net.kravuar.business.domain.exceptions.BusinessNotFoundException;

public interface BusinessManagementUseCase {
    /**
     * Creates of a {@link Business}.
     *
     * @param command the command containing information for business creation
     * @return Newly created {@link Business}
     * @throws BusinessNameAlreadyTaken if business name already taken
     */
    Business create(BusinessCreationCommand command);

    /**
     * Changes the name of a {@link Business}.
     *
     * @param command the command containing information for changing the business name
     * @throws BusinessNotFoundException if business wasn't found
     * @throws BusinessNameAlreadyTaken if business name already taken
     */
    void changeName(BusinessChangeNameCommand command);

    /**
     * Enables/disables a {@link Business}.
     *
     * @param command the command containing information for enabling/disabling business
     * @throws BusinessNotFoundException if business wasn't found
     */
    void changeActive(BusinessChangeActiveCommand command);
}