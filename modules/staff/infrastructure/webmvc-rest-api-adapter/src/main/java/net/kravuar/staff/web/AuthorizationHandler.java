package net.kravuar.staff.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.ports.in.BusinessRetrievalUseCase;
import net.kravuar.staff.ports.in.StaffRetrievalUseCase;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class AuthorizationHandler {
    private final BusinessRetrievalUseCase businessRetrieval;
    private final StaffRetrievalUseCase staffRetrieval;

    public boolean isOwnerOfBusiness(long businessId, String subject) {
        return businessRetrieval.findById(businessId, false).getOwnerSub().equals(subject);
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

    public boolean isOwnerOfStaffBusiness(long staffId, String subject) {
        Staff staff = staffRetrieval.findStaffById(staffId, true);
        return staff.getBusiness().getOwnerSub().equals(subject);
    }
}
