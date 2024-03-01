package net.kravuar.staff;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.staff.domain.Business;
import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.domain.StaffInvitation;
import net.kravuar.staff.domain.commands.RemoveStaffCommand;
import net.kravuar.staff.domain.commands.StaffAnswerInvitationCommand;
import net.kravuar.staff.domain.commands.StaffChangeDetailsCommand;
import net.kravuar.staff.domain.commands.StaffInvitationCommand;
import net.kravuar.staff.domain.exceptions.AccountNotFoundException;
import net.kravuar.staff.domain.exceptions.InvitationInvalidStatusException;
import net.kravuar.staff.ports.in.StaffManagementUseCase;
import net.kravuar.staff.ports.out.*;

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
    private final StaffLockPort staffLockPort;

    @Override
    public StaffInvitation sendInvitation(StaffInvitationCommand command) {
        try {
            staffLockPort.lock(command.businessId(), command.sub(), true);

            Business business = businessRetrievalPort.findById(command.businessId(), true);

            if (staffRetrievalPort.existsActiveByBusinessIdAndSub(command.businessId(), command.sub()))
                throw new IllegalStateException("Staff already exists");
            if (invitationRetrievalPort.existsWaitingByBusinessIdAndSub(command.businessId(), command.sub()))
                throw new IllegalStateException("Invitation already exists");
            if (!accountExistenceCheckPort.exists(command.sub()))
                throw new AccountNotFoundException();
            return invitationPersistencePort.save(
                    new StaffInvitation(
                            null,
                            command.sub(),
                            business
                    )
            );
        } finally {
            staffLockPort.lock(command.businessId(), command.sub(), false);
        }
    }

    @Override
    @Transactional
    public void answerInvitation(StaffAnswerInvitationCommand command) {
        StaffInvitation invitation = invitationRetrievalPort.findById(
                command.invitationId()
        );

        try {
            staffLockPort.lock(invitation.getBusiness().getId(), invitation.getSub(), true);

            StaffInvitation.Status status = invitation.getStatus();
            if (status != StaffInvitation.Status.WAITING)
                throw new InvitationInvalidStatusException(status);
            invitation.setStatus(
                    command.accept()
                            ? StaffInvitation.Status.ACCEPTED
                            : StaffInvitation.Status.DECLINED
            );
            invitationPersistencePort.save(invitation);
            if (command.accept()) {
                Staff staff = staffRetrievalPort.findByBusinessIdAndSub(
                        invitation.getBusiness().getId(),
                        invitation.getSub(),
                        false
                ).orElse(new Staff(
                        null,
                        invitation.getSub(),
                        invitation.getBusiness(),
                        true,
                        null
                ));
                staff.setActive(true);
                staffPersistencePort.save(staff);
                staffNotificationPort.notifyNewStaff(staff);
            }
        } finally {
            staffLockPort.lock(invitation.getBusiness().getId(), invitation.getSub(), false);
        }
    }

    @Override
    public void changeDetails(StaffChangeDetailsCommand command) {
        Staff staff = staffRetrievalPort.findById(command.staffId(), false);
        staff.setDescription(command.description());
        staffPersistencePort.save(staff);
    }

    @Override
    public void removeStaff(RemoveStaffCommand command) {
        Staff staff = staffRetrievalPort.findById(command.staffId(), true);
        staff.setActive(false);
        staffPersistencePort.save(staff);
        staffNotificationPort.notifyStaffActiveChanged(staff);
    }
}
