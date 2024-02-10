package net.kravuar.business.ports.in;

import jakarta.validation.Valid;
import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.commands.*;
import net.kravuar.business.domain.exceptions.*;

public interface BusinessManagementUseCase {
    // TODO: Encapsulate the regex of @Email annotation in custom annotation

    /**
     * Changes the name of a {@link Business}.
     *
     * @param command the command containing information for changing the business name
     * @throws BusinessNotFoundException if business wasn't found
     */
    void changeName(@Valid BusinessChangeNameCommand command);

    /**
     * Changes the email address of a {@link Business} if email verification code is correct.
     *
     * @param command the command containing information for changing the business email
     * @throws BusinessNotFoundException if business wasn't found
     */
    void changeEmail(@Valid BusinessChangeEmailCommand command);
}