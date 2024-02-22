package net.kravuar.staff.ports.in;

import jakarta.validation.Valid;
import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.domain.commands.StaffAnswerInvitationCommand;
import net.kravuar.staff.domain.commands.StaffInvitationCommand;
import net.kravuar.staff.domain.commands.StaffRemovalCommand;
import net.kravuar.staff.domain.exceptions.*;

public interface StaffManagementUseCase {
    /**
     * Sends invitation to business if not already invited.
     *
     * @param command the command containing information for {@link Staff} invitation
     * @return {@code false} if already invited, {@code true} otherwise
     * @throws BusinessNotFoundException if business to associate Staff with wasn't found
     * @throws BusinessDisabledException if business is disabled
     * @throws AccountNotFoundException if account to invite wasn't found
     * @throws IllegalStateException if there's an active invitation
     */
    boolean sendInvitation(@Valid StaffInvitationCommand command);

    /**
     * Answer on invitation to business.
     *
     * @param command the command containing information for answering {@link Staff} invitation
     * @throws InvitationNotFoundException if invitation wasn't found
     */
    void answerInvitation(@Valid StaffAnswerInvitationCommand command);

    /**
     * Removes staff from business
     * @param command the command containing information for {@link Staff} removal
     * @throws StaffNotFoundException if staff wasn't found
     */
    void removeStaff(@Valid StaffRemovalCommand command);
}