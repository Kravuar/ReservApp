package net.kravuar.business.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.Business;
import net.kravuar.business.ports.in.BusinessRetrievalUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/retrieval")
@RequiredArgsConstructor
class BusinessRetrievalController {
    private final BusinessRetrievalUseCase businessRetrievalUseCase;

    @GetMapping("/my")
    public Business byCurrentUser(@AuthenticationPrincipal OidcUser user) {
        return businessRetrievalUseCase.findBySub(user.getSubject());
    }

    @GetMapping("/byId/{id}")
    @PreAuthorize("permitAll()")
    public Business byId(@PathVariable("id") long id) {
        return businessRetrievalUseCase.findById(id);
    }

    @GetMapping("/byOwner/{sub}")
    @PreAuthorize("permitAll()")
    public Business byOwner(@PathVariable("sub") String sub) {
        return businessRetrievalUseCase.findBySub(sub);
    }

    @GetMapping("/active")
    @PreAuthorize("permitAll()")
    public List<Business> active() {
        return businessRetrievalUseCase.findAllActive();
    }
}
