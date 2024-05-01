package net.kravuar.business.ports.in;

import jakarta.validation.Valid;
import net.kravuar.business.domain.commands.BusinessChangeActiveCommand;
import net.kravuar.business.domain.commands.BusinessChangeDetailsCommand;
import net.kravuar.business.domain.commands.BusinessChangeNameCommand;
import net.kravuar.business.domain.commands.BusinessCreationCommand;
import net.kravuar.business.domain.exceptions.BusinessNameAlreadyTaken;
import net.kravuar.business.domain.exceptions.BusinessNotFoundException;
import net.kravuar.business.model.Business;
import net.kravuar.context.AppValidated;

@AppValidated
public interface BusinessManagementUseCase {
    /**
     * Create a {@link Business}.
     *
     * @param command the command containing information for business creation
     * @return Newly created {@link Business}
     * @throws BusinessNameAlreadyTaken if business name already taken
     */
    Business create(@Valid BusinessCreationCommand command);

    /**
     * Changes the name of a {@link Business}.
     *
     * @param command the command containing information for changing the business name
     * @return updated {@link Business}
     * @throws BusinessNotFoundException if business wasn't found
     * @throws BusinessNameAlreadyTaken  if business name already taken
     */
    Business changeName(@Valid BusinessChangeNameCommand command);

    /**
     * Enables/disables a {@link Business}.
     *
     * @param command the command containing information for enabling/disabling business
     * @return updated {@link Business}
     * @throws BusinessNotFoundException if business wasn't found
     */
    Business changeActive(BusinessChangeActiveCommand command);

    /**
     * Changes details for a {@link Business}.
     *
     * @param command the command containing information for details update of the business
     * @return updated {@link Business}
     * @throws BusinessNotFoundException if business wasn't found
     */
    Business changeDetails(@Valid BusinessChangeDetailsCommand command);
}