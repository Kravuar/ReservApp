package net.kravuar.business.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.commands.BusinessCreationCommand;
import net.kravuar.business.ports.in.BusinessCreationUseCase;
import net.kravuar.security.IdUserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business/creation")
@RequiredArgsConstructor
class BusinessCreationController {
    private final BusinessCreationUseCase businessCreation;

    // TODO: do i need dto's?

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public Business create(@AuthenticationPrincipal IdUserDetails principal, @RequestBody String name) {
        var command = new BusinessCreationCommand(
                principal.getId(),
                name
        );
        return businessCreation.create(command);
    }
}
