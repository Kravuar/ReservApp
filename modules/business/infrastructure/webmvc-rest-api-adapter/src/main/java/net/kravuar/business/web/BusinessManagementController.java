package net.kravuar.business.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.commands.BusinessChangeActiveCommand;
import net.kravuar.business.domain.commands.BusinessChangeNameCommand;
import net.kravuar.business.domain.commands.BusinessCreationCommand;
import net.kravuar.business.ports.in.BusinessManagementUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/management")
@RequiredArgsConstructor
class BusinessManagementController {
    private final BusinessManagementUseCase businessManagement;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    Business create(@AuthenticationPrincipal OidcUser user, @RequestBody String name) {
        var command = new BusinessCreationCommand(
                user.getSubject(),
                name
        );
        return businessManagement.create(command);
    }

    @PostMapping("/change-name")
    @PreAuthorize("isAuthenticated() && @businessRetrievalFacade.findById(#command.businessId()).ownerSub.equals(authentication.details.getSubject())")
    void changeName(BusinessChangeNameCommand command) {
        businessManagement.changeName(command);
    }

    @PutMapping("/change-active")
    @PreAuthorize("isAuthenticated() && @businessRetrievalFacade.findById(#command.businessId()).ownerSub.equals(authentication.details.getSubject())")
    void changeActive(BusinessChangeActiveCommand command) {
        businessManagement.changeActive(command);
    }
}
