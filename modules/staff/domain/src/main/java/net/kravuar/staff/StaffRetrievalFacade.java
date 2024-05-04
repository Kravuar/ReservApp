package net.kravuar.staff;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.pageable.Page;
import net.kravuar.staff.model.Staff;
import net.kravuar.staff.model.StaffDetailed;
import net.kravuar.staff.model.StaffInvitation;
import net.kravuar.staff.ports.in.StaffRetrievalUseCase;
import net.kravuar.staff.ports.out.AccountRetrievalPort;
import net.kravuar.staff.ports.out.InvitationRetrievalPort;
import net.kravuar.staff.ports.out.StaffRetrievalPort;

@AppComponent
@RequiredArgsConstructor
public class StaffRetrievalFacade implements StaffRetrievalUseCase {
    private final StaffRetrievalPort staffRetrievalPort;
    private final InvitationRetrievalPort invitationRetrievalPort;
    private final AccountRetrievalPort accountRetrievalPort;

    @Override
    public StaffDetailed findStaffById(long staffId, boolean activeOnly) {
        return withDetails(staffRetrievalPort.findById(staffId, activeOnly));
    }

    @Override
    public Page<StaffDetailed> findStaffByBusiness(long businessId, boolean activeOnly, int page, int pageSize) {
        Page<Staff> staffPage = staffRetrievalPort.findByBusiness(businessId, activeOnly, page, pageSize);
        return new Page<>(
                staffPage.content().stream()
                        .map(this::withDetails)
                        .toList(),
                staffPage.totalElements(),
                staffPage.totalPages()
        );
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

    private StaffDetailed withDetails(Staff staff) {
        return new StaffDetailed(
                staff,
                accountRetrievalPort.getBySub(staff.getSub())
        );
    }
}
