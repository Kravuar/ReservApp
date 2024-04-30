package net.kravuar.business.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.dto.BusinessDTO;
import net.kravuar.business.dto.DTOMapper;
import net.kravuar.business.model.Business;
import net.kravuar.business.ports.in.BusinessRetrievalUseCase;
import net.kravuar.pageable.Page;
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
class BusinessRetrievalController {
    private final BusinessRetrievalUseCase businessRetrieval;
    private final DTOMapper dtoMapper;

    @GetMapping("/my/{page}/{pageSize}")
    @PreAuthorize("isAuthenticated()")
    Page<BusinessDTO> my(@AuthenticationPrincipal Jwt user, @PathVariable("page") int page, @PathVariable("pageSize") int pageSize) {
        Page<Business> businesses = businessRetrieval
                .findBySub(
                        user.getSubject(),
                        false,
                        page,
                        pageSize
                );
        return new Page<>(
                businesses.content()
                        .stream()
                        .map(dtoMapper::toDTO)
                        .toList(),
                businesses.totalPages()
        );
    }

    @GetMapping("/my/by-id/{businessId}")
    @PreAuthorize("hasPermission(#businessId, 'Business', 'ReadDirect')")
    BusinessDTO myById(@PathVariable("businessId") long businessId) {
        return dtoMapper.toDTO(businessRetrieval.findById(businessId, false));
    }

    @GetMapping("/by-id/{businessId}")
    BusinessDTO byId(@PathVariable("businessId") long businessId) {
        return dtoMapper.toDTO(businessRetrieval.findById(businessId, true));
    }

    @GetMapping("/by-owner/{sub}/{page}/{pageSize}")
    Page<BusinessDTO> byOwner(@PathVariable("sub") String sub, @PathVariable("page") int page, @PathVariable("pageSize") int pageSize) {
        Page<Business> businesses = businessRetrieval
                .findBySub(
                        sub,
                        true,
                        page,
                        pageSize
                );
        return new Page<>(
                businesses.content()
                        .stream()
                        .map(dtoMapper::toDTO)
                        .toList(),
                businesses.totalPages()
        );
    }

    @GetMapping("/active/{page}/{pageSize}")
    Page<BusinessDTO> active(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize) {
        Page<Business> businesses = businessRetrieval
                .findActive(page, pageSize);
        return new Page<>(
                businesses.content()
                        .stream()
                        .map(dtoMapper::toDTO)
                        .toList(),
                businesses.totalPages()
        );
    }
}
