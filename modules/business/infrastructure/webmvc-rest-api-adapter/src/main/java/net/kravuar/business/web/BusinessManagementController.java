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

    @PutMapping("/change-name/{businessId}")
    @PreAuthorize("hasPermission(#businessId, 'Business', 'Update')")
    void changeName(@PathVariable("businessId") long businessId, @RequestBody String name) {
        businessManagement.changeName(new BusinessChangeNameCommand(
                businessId,
                name
        ));
    }

    @PutMapping("/change-active/{businessId}/{active}")
    @PreAuthorize("hasPermission(#businessId, 'Business', 'Update')")
    public void changeActive(@PathVariable("businessId") long businessId, @PathVariable("active") boolean active) {
        businessManagement.changeActive(new BusinessChangeActiveCommand(
                businessId,
                active
        ));
    }

    @PutMapping("/update-details/{businessId}")
    @PreAuthorize("hasPermission(#businessId, 'Business', 'Update')")
    public void updateDetails(@PathVariable("businessId") long businessId, @RequestBody BusinessDetailsDTO details) {
        businessManagement.changeDetails(new BusinessChangeDetailsCommand(
                businessId,
                details.description()
        ));
    }
}
