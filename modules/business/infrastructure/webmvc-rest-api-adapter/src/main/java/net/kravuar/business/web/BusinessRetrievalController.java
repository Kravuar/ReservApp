package net.kravuar.business.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.ports.in.BusinessRetrievalUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/retrieval")
@RequiredArgsConstructor
class BusinessRetrievalController {
    private final BusinessRetrievalUseCase businessRetrieval;
    private final DTOMapper dtoMapper;

    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    List<BusinessDTO> my(@AuthenticationPrincipal Jwt user) {
        return businessRetrieval
                .findAllBySub(user.getSubject(), false).stream()
                .map(dtoMapper::toDTO)
                .toList();
    }

    @GetMapping("/my/by-id/{businessId}")
    @PreAuthorize("isAuthenticated() && @authorizationHandler.isOwner(#businessId, authentication.details.subject)")
    BusinessDTO myById(@PathVariable("businessId") long businessId) {
        return dtoMapper.toDTO(businessRetrieval.findById(businessId, false));
    }

    @GetMapping("/by-id/{businessId}")
    BusinessDTO byId(@PathVariable("businessId") long businessId) {
        return dtoMapper.toDTO(businessRetrieval.findById(businessId, true));
    }

    @GetMapping("/byOwner/{sub}")
    List<BusinessDTO> byOwner(@PathVariable("sub") String sub) {
        return businessRetrieval
                .findAllBySub(sub, true).stream()
                .map(dtoMapper::toDTO)
                .toList();
    }

    @GetMapping("/active")
    List<BusinessDTO> active() {
        return businessRetrieval
                .findAllActive().stream()
                .map(dtoMapper::toDTO)
                .toList();
    }
}
