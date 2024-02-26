package net.kravuar.staff;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.staff.domain.Business;
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
    public Staff findStaffById(long id) {
        return staffRetrievalPort.findById(id);
    }

    @Override
    public List<Staff> findAllStaffByBusiness(long businessId) {
        Business business = businessRetrievalPort.findById(businessId);
        return staffRetrievalPort.findAllStaffByBusiness(business);
    }

    @Override
    public StaffInvitation findStaffInvitationById(long invitationId) {
        return invitationRetrievalPort.findById(invitationId);
    }

    @Override
    public List<StaffInvitation> findStaffInvitationBySubject(String sub) {
        return invitationRetrievalPort.findBySubject(sub);
    }

    @Override
    public List<StaffInvitation> findStaffInvitationsByBusiness(long businessId) {
        Business business = businessRetrievalPort.findById(businessId);
        return invitationRetrievalPort.findByBusiness(business);
    }
}
