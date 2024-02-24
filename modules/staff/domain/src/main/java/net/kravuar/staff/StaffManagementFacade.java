package net.kravuar.staff;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.staff.domain.Business;
import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.domain.StaffInvitation;
import net.kravuar.staff.domain.commands.StaffAnswerInvitationCommand;
import net.kravuar.staff.domain.commands.StaffDescriptionUpdateCommand;
import net.kravuar.staff.domain.commands.StaffInvitationCommand;
import net.kravuar.staff.domain.commands.StaffRemovalCommand;
import net.kravuar.staff.domain.exceptions.AccountNotFoundException;
import net.kravuar.staff.domain.exceptions.BusinessDisabledException;
import net.kravuar.staff.domain.exceptions.InvitationInvalidStatusException;
import net.kravuar.staff.ports.in.StaffManagementUseCase;
import net.kravuar.staff.ports.out.*;
import org.springframework.transaction.annotation.Transactional;

@AppComponent
@RequiredArgsConstructor
public class StaffManagementFacade implements StaffManagementUseCase {
    private final AccountExistenceCheckPort accountExistenceCheckPort;
    private final BusinessRetrievalPort businessRetrievalPort;
    private final StaffRetrievalPort staffRetrievalPort;
    private final StaffPersistencePort staffPersistencePort;
    private final StaffNotificationPort staffNotificationPort;
    private final InvitationPersistencePort invitationPersistencePort;
    private final InvitationRetrievalPort invitationRetrievalPort;

    @Override
    public synchronized StaffInvitation sendInvitation(StaffInvitationCommand command) {
        if (invitationRetrievalPort.existsByBusiness(command.sub(), command.businessId()))
            throw new IllegalStateException("Invitation already exists");
        Business business = businessRetrievalPort.findById(command.businessId());
        if (!business.isActive())
            throw new BusinessDisabledException();
        if (!accountExistenceCheckPort.exists(command.sub()))
            throw new AccountNotFoundException();
        return invitationPersistencePort.saveStaffInvitation(
                StaffInvitation.builder()
                        .business(business)
                        .sub(command.sub())
                        .build()
        );
    }

    @Override
    @Transactional
    public void answerInvitation(StaffAnswerInvitationCommand command) {
        StaffInvitation invitation = invitationRetrievalPort.findStaffInvitationByBusiness(
                command.invitationId()
        );
        StaffInvitation.Status status = invitation.getStatus();
        if (status != StaffInvitation.Status.WAITING)
            throw new InvitationInvalidStatusException(status);
        invitation.setStatus(
                command.accept()
                        ? StaffInvitation.Status.ACCEPTED
                        : StaffInvitation.Status.DECLINED
        );
        invitationPersistencePort.saveStaffInvitation(invitation);
        if (command.accept()) {
            Staff newStaff = Staff.builder()
                    .business(invitation.getBusiness())
                    .sub(invitation.getSub())
                    .active(true)
                    .build();
            staffPersistencePort.saveStaff(newStaff);
            staffNotificationPort.notifyNewStaff(newStaff);
        }
    }

    @Override
    public void updateDescription(StaffDescriptionUpdateCommand command) {
        Staff staff = staffRetrievalPort.findStaffById(command.staffId());
        staff.setDescription(command.description());
        staffPersistencePort.saveStaff(staff);
    }

    @Override
    public void removeStaff(StaffRemovalCommand command) {
        Staff staff = staffRetrievalPort.findStaffById(command.staffId());
        staff.setActive(false);
        staffPersistencePort.saveStaff(staff);
    }
}
