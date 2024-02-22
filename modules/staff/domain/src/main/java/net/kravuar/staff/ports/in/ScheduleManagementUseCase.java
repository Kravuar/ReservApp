package net.kravuar.staff.ports.in;

import net.kravuar.staff.domain.StaffSchedule;
import net.kravuar.staff.domain.commands.ChangeScheduleCommand;
import net.kravuar.staff.domain.exceptions.*;

public interface ScheduleManagementUseCase {
    /**
     * Update schedule for a staff member.
     *
     * @param command command containing details of the update
     * @return updated {@link StaffSchedule} object
     * @throws StaffNotFoundException if staff wasn't found
     * @throws ServiceNotFoundException if service wasn't found
     * @throws StaffIsntAssignedToServiceException if staff isn't assigned to service
     */
    StaffSchedule updateSchedule(ChangeScheduleCommand command);
}