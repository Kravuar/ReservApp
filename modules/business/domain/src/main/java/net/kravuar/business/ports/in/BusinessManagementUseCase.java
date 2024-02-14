package net.kravuar.business.ports.in;

import jakarta.validation.Valid;
import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.commands.BusinessChangeNameCommand;
import net.kravuar.business.domain.exceptions.BusinessNameAlreadyTaken;
import net.kravuar.business.domain.exceptions.BusinessNotFoundException;

public interface BusinessManagementUseCase {
    /**
     * Changes the name of a {@link Business}.
     *
     * @param command the command containing information for changing the business name
     * @throws BusinessNotFoundException if business wasn't found
     * @throws BusinessNameAlreadyTaken if business name already taken
     */
    void changeName(@Valid BusinessChangeNameCommand command);
}