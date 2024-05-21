package net.kravuar.staff.ports.in;

import jakarta.validation.Valid;
import net.kravuar.context.AppValidated;
import net.kravuar.staff.model.Staff;
import net.kravuar.staff.model.StaffDetailed;
import net.kravuar.staff.model.StaffInvitation;
import net.kravuar.staff.domain.commands.StaffChangeDetailsCommand;
import net.kravuar.staff.domain.commands.StaffInvitationCommand;
import net.kravuar.staff.domain.exceptions.*;

@AppValidated
public interface StaffManagementUseCase {
    /**
     * Sends invitation to business if not already invited.
     *
     * @param command the command containing information for {@link Staff} invitation
     * @return {@link StaffInvitation} created invitation
     * @throws BusinessNotFoundException if business to associate staff with wasn't found
     * @throws AccountNotFoundException  if account to invite wasn't found
     * @throws IllegalStateException     if there's an active invitation/staff
     */
    StaffInvitation sendInvitation(@Valid StaffInvitationCommand command);

    /**
     * Accept on invitation to business.
     *
     * @param invitationId id of the {@link StaffInvitation} to accept
     * @return {@code StaffDetailed} containing information of the created staff
     * @throws InvitationNotFoundException      if invitation wasn't found
     * @throws InvitationInvalidStatusException if invitation cannot be answered, due to invalid status
     */
    StaffDetailed acceptInvitation(Long invitationId);

    /**
     * Decline on invitation to business.
     *
     * @param invitationId id of the {@link StaffInvitation} to decline
     * @return {@code StaffInvitation} containing information of the declined invitation
     * @throws InvitationNotFoundException      if invitation wasn't found
     * @throws InvitationInvalidStatusException if invitation cannot be answered, due to invalid status
     */
    StaffInvitation declineInvitation(Long invitationId);

    /**
     * Changes details for a {@link Staff}.
     *
     * @param command the command containing information for details update of the staff
     * @return {@link StaffDetailed} updated staff
     * @throws StaffNotFoundException if staff wasn't found
     */
    StaffDetailed changeDetails(@Valid StaffChangeDetailsCommand command);

    /**
     * Removes staff from business
     *
     * @param staffId identifier of {@link Staff} to remove
     * @return {@link Staff} removed staff
     * @throws StaffNotFoundException if staff wasn't found
     */
    StaffDetailed removeStaff(long staffId);
}