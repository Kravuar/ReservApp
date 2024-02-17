package net.kravuar.accounts.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.accounts.ports.in.AccountRetrievalUseCase;
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
class AccountRetrievalController {
    private final AccountRetrievalUseCase retrieval;
    private final AccountDTOMapper mapper;

    @GetMapping("/user-details")
    @PreAuthorize("isAuthenticated()")
    AccountDTO userDetails(@AuthenticationPrincipal Jwt jwt) {
        return mapper.toDto(retrieval.findByUsername(jwt.getSubject()));
    }
}
