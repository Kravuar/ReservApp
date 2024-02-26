package net.kravuar.staff.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.staff.ports.in.BusinessRetrievalUseCase;
import net.kravuar.staff.ports.in.StaffRetrievalUseCase;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorizationHandler {
    private final BusinessRetrievalUseCase businessRetrieval;
    private final StaffRetrievalUseCase staffRetrieval;

    public boolean isOwnerOfBusiness(long businessId, String subject) {
        return businessRetrieval.findById(businessId).getOwnerSub().equals(subject);
    }

    public boolean isStaff(long staffId, String subject) {
        return staffRetrieval.findStaffById(staffId).getSub().equals(subject);
    }

    public boolean isInvitedStaff(long invitationId, String subject) {
        return staffRetrieval.findStaffInvitationById(invitationId).getSub().equals(subject);
    }
}
