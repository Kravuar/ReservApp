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
    private final StaffLockPort staffLockPort;

    @Override
    public StaffInvitation sendInvitation(StaffInvitationCommand command) {
        try {
            staffLockPort.lock(command.businessId(), command.sub(), true);

            Business business = businessRetrievalPort.findById(command.businessId());

            if (invitationRetrievalPort.existsByBusinessAndSub(business, command.sub()))
                throw new IllegalStateException("Invitation already exists");
            if (!business.isActive())
                throw new BusinessDisabledException();
            if (!accountExistenceCheckPort.exists(command.sub()))
                throw new AccountNotFoundException();
            return invitationPersistencePort.save(
                    StaffInvitation.builder()
                            .business(business)
                            .sub(command.sub())
                            .build()
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
                Staff newStaff = Staff.builder()
                        .business(invitation.getBusiness())
                        .sub(invitation.getSub())
                        .active(true)
                        .build();
                staffPersistencePort.save(newStaff);
                staffNotificationPort.notifyNewStaff(newStaff);
            }
        } finally {
            staffLockPort.lock(invitation.getBusiness().getId(), invitation.getSub(), false);
        }
    }

    @Override
    public void updateDescription(StaffDescriptionUpdateCommand command) {
        try {
            staffLockPort.lock(command.staffId(), true);

            Staff staff = staffRetrievalPort.findById(command.staffId());
            staff.setDescription(command.description());
            staffPersistencePort.save(staff);
        } finally {
            staffLockPort.lock(command.staffId(), false);
        }
    }

    @Override
    public void removeStaff(StaffRemovalCommand command) {
        try {
            staffLockPort.lock(command.staffId(), true);

            Staff staff = staffRetrievalPort.findById(command.staffId());
            staff.setActive(false);
            staffPersistencePort.save(staff);
        } finally {
            staffLockPort.lock(command.staffId(), false);
        }
    }
}
