package net.kravuar.services.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.services.ports.in.BusinessRetrievalUseCase;
import net.kravuar.services.ports.in.ServiceRetrievalUseCase;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorizationHandler {
    private final BusinessRetrievalUseCase businessRetrieval;
    private final ServiceRetrievalUseCase serviceRetrieval;

    public boolean isOwnerOfBusiness(long businessId, String subject) {
        return businessRetrieval.findById(businessId).getOwnerSub().equals(subject);
    }

    public boolean isOwnerOfService(long serviceId, String subject) {
        return serviceRetrieval.findById(serviceId).getBusiness().getOwnerSub().equals(subject);
    }
}
