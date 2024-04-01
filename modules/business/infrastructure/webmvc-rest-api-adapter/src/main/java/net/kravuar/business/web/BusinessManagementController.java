package net.kravuar.business.web;

import lombok.RequiredArgsConstructor;
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
    private final DTOMapper dtoMapper;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    BusinessDTO create(@AuthenticationPrincipal Jwt user, @RequestBody BusinessCreationDTO businessCreation) {
        var command = new BusinessCreationCommand(
                user.getSubject(),
                businessCreation.name(),
                businessCreation.description()
        );
        return dtoMapper.toDTO(businessManagement.create(command));
    }

    @PostMapping("/change-name")
    @PreAuthorize("isAuthenticated() && hasPermission(#command.businessId(), 'Business', 'Update')")
    void changeName(@RequestBody BusinessChangeNameCommand command) {
        businessManagement.changeName(command);
    }

    @PutMapping("/change-active")
    @PreAuthorize("isAuthenticated() && hasPermission(#command.businessId(), 'Business', 'Update')")
    void changeActive(@RequestBody BusinessChangeActiveCommand command) {
        businessManagement.changeActive(command);
    }

    @PutMapping("/update-details")
    @PreAuthorize("isAuthenticated() && hasPermission(#command.businessId(), 'Business', 'Update')")
    void updateDetails(@RequestBody BusinessChangeDetailsCommand command) {
        businessManagement.changeDetails(command);
    }
}
