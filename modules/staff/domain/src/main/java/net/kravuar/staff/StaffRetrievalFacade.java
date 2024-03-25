package net.kravuar.staff;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.pageable.Page;
import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.domain.StaffInvitation;
import net.kravuar.staff.ports.in.StaffRetrievalUseCase;
import net.kravuar.staff.ports.out.InvitationRetrievalPort;
import net.kravuar.staff.ports.out.StaffRetrievalPort;

@AppComponent
@RequiredArgsConstructor
public class StaffRetrievalFacade implements StaffRetrievalUseCase {
    private final StaffRetrievalPort staffRetrievalPort;
    private final InvitationRetrievalPort invitationRetrievalPort;

    @Override
    public Staff findStaffById(long staffId, boolean activeOnly) {
        return staffRetrievalPort.findById(staffId, activeOnly);
    }

    @Override
    public Page<Staff> findStaffByBusiness(long businessId, boolean activeOnly, int page, int pageSize) {
        return staffRetrievalPort.findByBusiness(businessId, activeOnly, page, pageSize);
    }

    @Override
    public StaffInvitation findStaffInvitationById(long invitationId) {
        return invitationRetrievalPort.findById(invitationId);
    }

    @Override
    public Page<StaffInvitation> findStaffInvitationsBySubject(String sub, int page, int pageSize) {
        return invitationRetrievalPort.findBySubject(sub, page, pageSize);
    }

    @Override
    public Page<StaffInvitation> findStaffInvitationsByBusiness(long businessId, int page, int pageSize) {
        return invitationRetrievalPort.findByBusiness(businessId, page, pageSize);
    }
}
