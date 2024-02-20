package net.kravuar.business.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.commands.BusinessCreationCommand;
import net.kravuar.business.ports.in.BusinessCreationUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/creation")
@RequiredArgsConstructor
class BusinessCreationController {
    private final BusinessCreationUseCase businessCreation;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public Business create(@AuthenticationPrincipal OidcUser user, @RequestBody String name) {
        var command = new BusinessCreationCommand(
                user.getSubject(),
                name
        );
        return businessCreation.create(command);
    }
}
