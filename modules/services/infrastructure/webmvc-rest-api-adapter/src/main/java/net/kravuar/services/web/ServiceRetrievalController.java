package net.kravuar.services.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.pageable.Page;
import net.kravuar.services.domain.Service;
import net.kravuar.services.ports.in.ServiceRetrievalUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/retrieval")
@RequiredArgsConstructor
class ServiceRetrievalController {
    private final ServiceRetrievalUseCase serviceRetrieval;
    private final DTOServiceMapper dtoServiceMapper;

    @GetMapping("/my/by-business/{businessId}/{page}/{pageSize}")
    @PreAuthorize("hasPermission(#businessId, 'Service', 'Read')")
    Page<ServiceDTO> myByBusiness(@PathVariable("businessId") long businessId, @PathVariable("page") int page, @PathVariable("pageSize") int pageSize) {
        Page<Service> services = serviceRetrieval
                .findActiveByActiveBusinessId(
                        businessId,
                        false,
                        page,
                        pageSize
                );
        return new Page<>(
                services.content().stream()
                        .map(dtoServiceMapper::toDTO)
                        .toList(),
                services.totalPages()
        );
    }

    @GetMapping("/my/by-id/{serviceId}")
    @PreAuthorize("hasPermission(#serviceId, 'Service', 'ReadDirect')")
    ServiceDTO myById(@PathVariable("serviceId") long serviceId) {
        return dtoServiceMapper.toDTO(serviceRetrieval.findById(serviceId, false));
    }

    @GetMapping("/by-business/{businessId}/{page}/{pageSize}")
    Page<ServiceDTO> byBusiness(@PathVariable("businessId") long businessId, @PathVariable("page") int page, @PathVariable("pageSize") int pageSize) {
        Page<Service> services = serviceRetrieval
                .findActiveByActiveBusinessId(
                        businessId,
                        true,
                        page,
                        pageSize
                );
        return new Page<>(
                services.content().stream()
                        .map(dtoServiceMapper::toDTO)
                        .toList(),
                services.totalPages()
        );
    }

    @GetMapping("/by-id/{serviceId}")
    ServiceDTO byId(@PathVariable("serviceId") long serviceId) {
        return dtoServiceMapper.toDTO(serviceRetrieval.findById(serviceId, true));
    }

    @GetMapping("/active/{page}/{pageSize}")
    Page<ServiceDTO> allActive(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize) {
        Page<Service> services = serviceRetrieval.findActive(page, pageSize);
        return new Page<>(
                services.content().stream()
                        .map(dtoServiceMapper::toDTO)
                        .toList(),
                services.totalPages()
        );
    }
}
