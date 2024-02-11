package net.kravuar.business.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.commands.BusinessCreationCommand;
import net.kravuar.business.domain.commands.BusinessEmailVerificationCommand;
import net.kravuar.business.domain.exceptions.MessageSendingException;
import net.kravuar.business.ports.in.BusinessCreationUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business/creation")
@RequiredArgsConstructor
class BusinessCreationController {
    private final BusinessCreationUseCase businessCreation;

    // TODO: do i need dto's?

    @PostMapping("/create")
    public Business register(BusinessCreationCommand command) {
        return businessCreation.create(command);
    }

    @PostMapping("/send-verification")
    public void sendVerification(BusinessEmailVerificationCommand command) throws MessageSendingException {
        businessCreation.sendVerification(command);
    }
}
