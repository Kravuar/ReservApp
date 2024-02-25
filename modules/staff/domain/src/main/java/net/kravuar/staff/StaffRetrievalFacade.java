package net.kravuar.staff;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.domain.StaffInvitation;
import net.kravuar.staff.ports.in.StaffRetrievalUseCase;
import net.kravuar.staff.ports.out.InvitationRetrievalPort;
import net.kravuar.staff.ports.out.StaffRetrievalPort;

import java.util.List;

@AppComponent
@RequiredArgsConstructor
public class StaffRetrievalFacade implements StaffRetrievalUseCase {
    private final StaffRetrievalPort staffRetrievalPort;
    private final InvitationRetrievalPort invitationRetrievalPort;

    @Override
    public Staff findStaffById(long id) {
        return staffRetrievalPort.findStaffById(id);
    }

    @Override
    public List<Staff> findAllStaffByBusiness(long businessId) {
        return staffRetrievalPort.findAllStaffByBusiness(businessId);
    }

    @Override
    public List<StaffInvitation> findStaffInvitationBySubject(String sub) {
        return invitationRetrievalPort.findStaffInvitationBySubject(sub);
    }

    @Override
    public List<StaffInvitation> findStaffInvitationsByBusiness(long businessId) {
        return invitationRetrievalPort.findStaffInvitationsByBusiness(businessId);
    }
}
