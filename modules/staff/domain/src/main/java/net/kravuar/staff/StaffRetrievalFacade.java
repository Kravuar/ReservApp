package net.kravuar.staff;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.domain.StaffInvitation;
import net.kravuar.staff.ports.in.StaffRetrievalUseCase;
import net.kravuar.staff.ports.out.BusinessRetrievalPort;
import net.kravuar.staff.ports.out.InvitationRetrievalPort;
import net.kravuar.staff.ports.out.StaffRetrievalPort;

import java.util.List;

@AppComponent
@RequiredArgsConstructor
public class StaffRetrievalFacade implements StaffRetrievalUseCase {
    private final BusinessRetrievalPort businessRetrievalPort;
    private final StaffRetrievalPort staffRetrievalPort;
    private final InvitationRetrievalPort invitationRetrievalPort;

    @Override
    public Staff findStaffById(long staffId) {
        return staffRetrievalPort.findById(staffId);
    }

    @Override
    public List<Staff> findAllStaffByBusiness(long businessId) {
        return staffRetrievalPort.findAllStaffByBusinessId(businessId);
    }

    @Override
    public StaffInvitation findStaffInvitationById(long invitationId) {
        return invitationRetrievalPort.findById(invitationId);
    }

    @Override
    public List<StaffInvitation> findStaffInvitationsBySubject(String sub) {
        return invitationRetrievalPort.findBySubject(sub);
    }

    @Override
    public List<StaffInvitation> findStaffInvitationsByBusiness(long businessId) {
        return invitationRetrievalPort.findByBusinessId(businessId);
    }
}
