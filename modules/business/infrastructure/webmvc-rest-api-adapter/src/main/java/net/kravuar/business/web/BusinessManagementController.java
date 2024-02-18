package net.kravuar.business.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.commands.BusinessChangeNameCommand;
import net.kravuar.business.ports.in.BusinessManagementUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
@RequiredArgsConstructor
class BusinessManagementController {
    private final BusinessManagementUseCase businessManagement;

    // Only if the requested business owner matches authentication
    @PostMapping("/change-name")
    @PreAuthorize("isAuthenticated() && businessRetrievalFacade.findById(#command.businessId()).ownerSub.equals(authentication.details.ownerSubject)")
    public void changeName(BusinessChangeNameCommand command) {
        businessManagement.changeName(command);
    }
}
