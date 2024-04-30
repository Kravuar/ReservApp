package net.kravuar.services.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.services.domain.commands.ServiceChangeActiveCommand;
import net.kravuar.services.domain.commands.ServiceChangeDetailsCommand;
import net.kravuar.services.domain.commands.ServiceCreationCommand;
import net.kravuar.services.dto.DTOServiceMapper;
import net.kravuar.services.dto.ServiceDTO;
import net.kravuar.services.dto.ServiceDetailsDTO;
import net.kravuar.services.ports.in.ServiceManagementUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/management")
@RequiredArgsConstructor
class ServiceManagementController {
    private final ServiceManagementUseCase serviceManagement;
    private final DTOServiceMapper dtoServiceMapper;

    @PostMapping("/create/{businessId}")
    @PreAuthorize("hasPermission(#businessId, 'Service', 'Create')")
    ServiceDTO create(@PathVariable("businessId") long businessId, @RequestBody ServiceDetailsDTO command) {
        return dtoServiceMapper.toDTO(serviceManagement.create(new ServiceCreationCommand(
                businessId,
                command.name(),
                command.description()
        )));
    }

    @PutMapping("/change-active/{serviceId}/{active}")
    @PreAuthorize("hasPermission(#serviceId, 'Service', 'Update')")
    public void changeActive(@PathVariable("serviceId") long serviceId, @PathVariable("active") boolean active) {
        serviceManagement.changeActive(new ServiceChangeActiveCommand(
                serviceId,
                active
        ));
    }

    @PutMapping("/update-details/{serviceId}")
    @PreAuthorize("hasPermission(#serviceId, 'Service', 'Update')")
    public void updateDetails(@PathVariable("serviceId") long serviceId, @RequestBody ServiceDetailsDTO details) {
        serviceManagement.changeDetails(new ServiceChangeDetailsCommand(
                serviceId,
                details.name(),
                details.description()
        ));
    }
}
