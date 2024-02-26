package net.kravuar.staff.ports.in;

import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.domain.StaffInvitation;
import net.kravuar.staff.domain.commands.StaffAnswerInvitationCommand;
import net.kravuar.staff.domain.commands.StaffChangeDetailsCommand;
import net.kravuar.staff.domain.commands.StaffInvitationCommand;
import net.kravuar.staff.domain.commands.StaffRemovalCommand;
import net.kravuar.staff.domain.exceptions.*;

public interface StaffManagementUseCase {
    /**
     * Sends invitation to business if not already invited.
     *
     * @param command the command containing information for {@link Staff} invitation
     * @return {@link StaffInvitation} created invitation
     * @throws BusinessNotFoundException if business to associate Staff with wasn't found
     * @throws BusinessDisabledException if business is disabled
     * @throws AccountNotFoundException if account to invite wasn't found
     * @throws IllegalStateException if there's an active invitation
     */
    StaffInvitation sendInvitation(StaffInvitationCommand command);

    /**
     * Answer on invitation to business.
     *
     * @param command the command containing information for answering {@link Staff} invitation
     * @throws InvitationNotFoundException if invitation wasn't found
     * @throws InvitationInvalidStatusException if invitation cannot be answered, due to invalid status
     */
    void answerInvitation(StaffAnswerInvitationCommand command);

    /**
     * Changes details for a {@link Staff}.
     *
     * @param command the command containing information for details update of the staff
     * @throws StaffNotFoundException if staff wasn't found
     */
    void changeDetails(StaffChangeDetailsCommand command);

    /**
     * Removes staff from business
     *
     * @param command the command containing information for {@link Staff} removal
     * @throws StaffNotFoundException if staff wasn't found
     */
    void removeStaff(StaffRemovalCommand command);
}