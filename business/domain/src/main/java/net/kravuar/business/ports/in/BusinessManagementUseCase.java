package net.kravuar.business.ports.in;

import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.commands.BusinessCreationCommand;
import net.kravuar.business.domain.commands.BusinessEditNameCommand;
import net.kravuar.business.domain.commands.BusinessEmailChangeCommand;
import net.kravuar.business.domain.exceptions.MessageSendingException;

public interface BusinessManagementUseCase {
    Business create(BusinessCreationCommand command) throws MessageSendingException;
    void editName(BusinessEditNameCommand command);
    void changeEmail(BusinessEmailChangeCommand command) throws MessageSendingException;
}
