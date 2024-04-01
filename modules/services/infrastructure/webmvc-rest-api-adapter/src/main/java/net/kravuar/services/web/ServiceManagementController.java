package net.kravuar.services.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.services.domain.commands.ServiceChangeActiveCommand;
import net.kravuar.services.domain.commands.ServiceChangeDetailsCommand;
import net.kravuar.services.domain.commands.ServiceCreationCommand;
import net.kravuar.services.ports.in.ServiceManagementUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/management")
@RequiredArgsConstructor
class ServiceManagementController {
    private final ServiceManagementUseCase serviceManagement;
    private final DTOServiceMapper dtoServiceMapper;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated() && hasPermission(#command.businessId(), 'Service', 'Create')")
    ServiceDTO create(@RequestBody ServiceCreationCommand command) {
        return dtoServiceMapper.toDTO(serviceManagement.create(command));
    }

    @PutMapping("/change-active")
    @PreAuthorize("isAuthenticated() && hasPermission(#command.serviceId(), 'Service', 'Update')")
    public void changeActive(@RequestBody ServiceChangeActiveCommand command) {
        serviceManagement.changeActive(command);
    }

    @PutMapping("/update-details")
    @PreAuthorize("isAuthenticated() && hasPermission(#command.serviceId(), 'Service', 'Update')")
    public void updateDetails(@RequestBody ServiceChangeDetailsCommand command) {
        serviceManagement.changeDetails(command);
    }
}
