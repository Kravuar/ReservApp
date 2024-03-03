package net.kravuar.services.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.services.ports.in.ServiceRetrievalUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/retrieval")
@RequiredArgsConstructor
class ServiceRetrievalController {
    private final ServiceRetrievalUseCase serviceRetrieval;
    private final DTOServiceMapper dtoServiceMapper;

    @GetMapping("/my/by-business/{businessId}")
    @PreAuthorize("isAuthenticated() && @authorizationHandler.isOwnerOfBusiness(#businessId, authentication.details.subject)")
    List<ServiceDTO> myByBusiness(@PathVariable("businessId") long businessId) {
        return serviceRetrieval
                .findAllByActiveBusinessId(businessId, false).stream()
                .map(dtoServiceMapper::toDTO)
                .toList();
    }

    @GetMapping("/my/byId/{serviceId}")
    @PreAuthorize("isAuthenticated() && @authorizationHandler.isOwnerOfService(#serviceId, authentication.details.subject)")
    ServiceDTO myById(@PathVariable("serviceId") long serviceId) {
        return dtoServiceMapper.toDTO(serviceRetrieval.findById(serviceId, false));
    }

    @GetMapping("/by-business/{businessId}")
    List<ServiceDTO> byBusiness(@PathVariable("businessId") long businessId) {
        return serviceRetrieval
                .findAllByActiveBusinessId(businessId, true).stream()
                .map(dtoServiceMapper::toDTO)
                .toList();
    }

    @GetMapping("/byId/{serviceId}")
    ServiceDTO byId(@PathVariable("serviceId") long serviceId) {
        return dtoServiceMapper.toDTO(serviceRetrieval.findById(serviceId, true));
    }

    @GetMapping("/active")
    List<ServiceDTO> allActive() {
        return serviceRetrieval.findAll().stream()
                .map(dtoServiceMapper::toDTO)
                .toList();
    }
}
