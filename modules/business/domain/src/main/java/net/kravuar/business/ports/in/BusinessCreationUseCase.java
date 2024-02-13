package net.kravuar.business.ports.in;

import jakarta.validation.Valid;
import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.commands.BusinessCreationCommand;
import net.kravuar.business.domain.exceptions.BusinessNameAlreadyTaken;
import net.kravuar.business.domain.exceptions.EmailNotVerifiedException;

public interface BusinessCreationUseCase {
    /**
     * Creates of a {@link Business}.
     *
     * @param command the command containing information for business creation
     * @return Newly created {@link Business}
     * @throws EmailNotVerifiedException if owner email isn't verified yet
     * @throws BusinessNameAlreadyTaken if business name already taken
     */
    Business create(@Valid BusinessCreationCommand command);
}