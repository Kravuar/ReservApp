package net.kravuar.services.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.services.ports.in.BusinessRetrievalUseCase;
import net.kravuar.services.ports.in.ServiceRetrievalUseCase;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
class AuthorizationHandler implements PermissionEvaluator {
    private final BusinessRetrievalUseCase businessRetrieval;
    private final ServiceRetrievalUseCase serviceRetrieval;
    private final Map<String, Map<String, BiFunction<String, Object, Boolean>>> permissionEvaluators = Map.of(
            "Service", Map.of(
                    "Create", (subject, id) -> isOwnerOfBusiness((long) id, subject),
                    "Read", (subject, id) -> isOwnerOfBusiness((long) id, subject),
                    "Update", (subject, id) -> isOwnerOfService((long) id, subject)
            )
    );

    public boolean isOwnerOfBusiness(long businessId, String subject) {
        return businessRetrieval.findById(businessId).getOwnerSub().equals(subject);
    }

    public boolean isOwnerOfService(long serviceId, String subject) {
        return serviceRetrieval.findById(serviceId, false).getBusiness().getOwnerSub().equals(subject);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        Map<String, BiFunction<String, Object, Boolean>> permissions = permissionEvaluators.get(targetType);
        if (permissions == null)
            return false;
        BiFunction<String, Object, Boolean> evaluator = permissions.get((String) permission);
        if (evaluator == null)
            return false;
        return evaluator.apply(((Jwt) authentication.getPrincipal()).getSubject(), targetId);
    }
}
