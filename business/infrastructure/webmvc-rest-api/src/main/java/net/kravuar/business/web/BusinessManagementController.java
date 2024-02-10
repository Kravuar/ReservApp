package net.kravuar.business.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.commands.BusinessChangeEmailCommand;
import net.kravuar.business.domain.commands.BusinessChangeNameCommand;
import net.kravuar.business.ports.in.BusinessManagementUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
@RequiredArgsConstructor
class BusinessManagementController {
    private final BusinessManagementUseCase businessManagement;

    // TODO: do i need dto's?

    @PostMapping("/change-name")
    public void changeName(BusinessChangeNameCommand command) {
        businessManagement.changeName(command);
    }

    @PostMapping("/change-email")
    public void changeEmail(BusinessChangeEmailCommand command) {
        businessManagement.changeEmail(command);
    }
}
