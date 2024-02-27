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
    List<BusinessDTO> byCurrentUser(@AuthenticationPrincipal Jwt user) {
        return businessRetrieval.findActiveBySub(user.getSubject()).stream()
                .map(dtoMapper::toDTO)
                .toList();
    }

    @GetMapping("/byId/{id}")
    BusinessDTO byId(@PathVariable("id") long id) {
        return dtoMapper.toDTO(businessRetrieval.findById(id));
    }

    @GetMapping("/byOwner/{sub}")
    List<BusinessDTO> byOwner(@PathVariable("sub") String sub) {
        return businessRetrieval.findActiveBySub(sub).stream()
                .map(dtoMapper::toDTO)
                .toList();
    }

    @GetMapping("/active")
    List<BusinessDTO> active() {
        return businessRetrieval.findAllActive().stream()
                .map(dtoMapper::toDTO)
                .toList();
    }
}
