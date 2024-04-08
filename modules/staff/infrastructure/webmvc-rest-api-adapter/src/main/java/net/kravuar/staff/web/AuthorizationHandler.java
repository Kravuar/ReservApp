package net.kravuar.staff.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.domain.StaffInvitation;
import net.kravuar.staff.ports.in.BusinessRetrievalUseCase;
import net.kravuar.staff.ports.in.StaffRetrievalUseCase;
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
    private final StaffRetrievalUseCase staffRetrieval;
    private final Map<String, Map<String, BiFunction<String, Object, Boolean>>> permissionEvaluators = Map.of(
            "Staff", Map.of(
                    "Delete", (subject, id) -> isOwnerOfStaffBusiness((long) id, subject),
                    "Update", (subject, id) -> isStaffOrOwnerOfStaffBusiness((long) id, subject)
            ),
            "Invitation", Map.of(
                    "Read", (subject, id) -> isOwnerOfBusiness((long) id, subject),
                    "Invite", (subject, id) -> isOwnerOfBusiness((long) id, subject),
                    "AcceptInvitation", (subject, id) -> isInvitedStaff((long) id, subject),
                    "DeclineInvitation", (subject, id) -> isInvitedStaffOrBusinessOwner((long) id, subject)
            )
    );

    public boolean isOwnerOfBusiness(long businessId, String subject) {
        return businessRetrieval.findById(businessId).getOwnerSub().equals(subject);
    }

    public boolean isStaffOrOwnerOfStaffBusiness(long staffId, String subject) {
        return isStaff(staffId, subject) || isOwnerOfStaffBusiness(staffId, subject);
    }

    public boolean isStaff(long staffId, String subject) {
        return staffRetrieval.findStaffById(staffId, true).getSub().equals(subject);
    }

    public boolean isInvitedStaff(long invitationId, String subject) {
        return staffRetrieval.findStaffInvitationById(invitationId).getSub().equals(subject);
    }

    public boolean isInvitedStaffOrBusinessOwner(long invitationId, String subject) {
        StaffInvitation invitation = staffRetrieval.findStaffInvitationById(invitationId);
        return invitation.getSub().equals(subject) ||
                invitation.getBusiness().getOwnerSub().equals(subject);
    }

    public boolean isOwnerOfStaffBusiness(long staffId, String subject) {
        Staff staff = staffRetrieval.findStaffById(staffId, true);
        return staff.getBusiness().getOwnerSub().equals(subject);
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
