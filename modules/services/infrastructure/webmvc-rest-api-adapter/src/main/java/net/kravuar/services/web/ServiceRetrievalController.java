package net.kravuar.services.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.pageable.Page;
import net.kravuar.services.dto.DTOServiceMapper;
import net.kravuar.services.dto.ServiceDTO;
import net.kravuar.services.model.Service;
import net.kravuar.services.ports.in.ServiceRetrievalUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
                services.totalElements(),
                services.totalPages()
        );
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
                services.totalElements(),
                services.totalPages()
        );
    }

    @GetMapping("/by-id/{serviceId}")
    ServiceDTO byId(@PathVariable("serviceId") long serviceId, @AuthenticationPrincipal Jwt jwt) {
        String sub = jwt == null
                ? null
                : jwt.getSubject();
        return dtoServiceMapper.toDTO(serviceRetrieval.findByIdAndSub(serviceId, sub));
    }

    @GetMapping("/active/{page}/{pageSize}")
    Page<ServiceDTO> allActive(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize) {
        Page<Service> services = serviceRetrieval.findActive(page, pageSize);
        return new Page<>(
                services.content().stream()
                        .map(dtoServiceMapper::toDTO)
                        .toList(),
                services.totalElements(),
                services.totalPages()
        );
    }
}
