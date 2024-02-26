package net.kravuar.business.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.commands.BusinessChangeActiveCommand;
import net.kravuar.business.domain.commands.BusinessChangeDetailsCommand;
import net.kravuar.business.domain.commands.BusinessChangeNameCommand;
import net.kravuar.business.domain.commands.BusinessCreationCommand;
import net.kravuar.business.ports.in.BusinessManagementUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/management")
@RequiredArgsConstructor
class BusinessManagementController {
    private final BusinessManagementUseCase businessManagement;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    Business create(@AuthenticationPrincipal Jwt user, @RequestBody BusinessCreationDTO businessCreation) {
        var command = new BusinessCreationCommand(
                user.getSubject(),
                businessCreation.name()
        );
        return businessManagement.create(command);
    }

    @PostMapping("/change-name")
    @PreAuthorize("isAuthenticated() && @authorizationHandler.isOwner(#command.businessId(), authentication.details.subject)")
    void changeName(@RequestBody BusinessChangeNameCommand command) {
        businessManagement.changeName(command);
    }

    @PutMapping("/change-active")
    @PreAuthorize("isAuthenticated() && @authorizationHandler.isOwner(#command.businessId(), authentication.details.subject)")
    void changeActive(@RequestBody BusinessChangeActiveCommand command) {
        businessManagement.changeActive(command);
    }

    @PutMapping("/update-details")
    @PreAuthorize("isAuthenticated() && @authorizationHandler.isOwner(#command.businessId(), authentication.details.subject)")
    public void updateDetails(@RequestBody BusinessChangeDetailsCommand command) {
        businessManagement.changeDetails(command);
    }
}
