package net.kravuar.services.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.services.domain.Service;
import net.kravuar.services.domain.commands.ServiceChangeActiveCommand;
import net.kravuar.services.domain.commands.ServiceChangeNameCommand;
import net.kravuar.services.domain.commands.ServiceCreationCommand;
import net.kravuar.services.ports.in.ServiceManagementUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/management")
@RequiredArgsConstructor
class ServiceManagementController {
    private final ServiceManagementUseCase serviceManagement;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated() && @businessRetrievalFacade.findById(#command.businessId).ownerSub.equals(authentication.details.getSubject())")
    Service create(@RequestBody ServiceCreationCommand command) {
        return serviceManagement.create(command);
    }

    @PostMapping("/change-name")
    @PreAuthorize("isAuthenticated() && @serviceRetrievalFacade.findById(#command.serviceId).business.ownerSub.equals(authentication.details.getSubject())")
    public void changeName(ServiceChangeNameCommand command) {
        serviceManagement.changeName(command);
    }

    @PutMapping("/change-active")
    @PreAuthorize("isAuthenticated() && @serviceRetrievalFacade.findById(#command.serviceId).business.ownerSub.equals(authentication.details.getSubject())")
    public void changeName(ServiceChangeActiveCommand command) {
        serviceManagement.changeActive(command);
    }
}
