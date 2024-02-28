package net.kravuar.business.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.ports.in.BusinessRetrievalUseCase;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class AuthorizationHandler {
    private final BusinessRetrievalUseCase businessRetrieval;

    public boolean isOwner(long businessId, String subject) {
        return businessRetrieval.findById(businessId, false).getOwnerSub().equals(subject);
    }
}
