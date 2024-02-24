package net.kravuar.staff.ports.in;

import net.kravuar.staff.domain.commands.ChangeDailyScheduleCommand;
import net.kravuar.staff.domain.exceptions.ServiceNotFoundException;
import net.kravuar.staff.domain.exceptions.StaffIsntAssignedToServiceException;
import net.kravuar.staff.domain.exceptions.StaffNotFoundException;

public interface ScheduleManagementUseCase {
    /**
     * Update schedule for a staff member.
     *
     * @param command command containing details of the update
     * @throws StaffNotFoundException if staff wasn't found
     * @throws ServiceNotFoundException if service wasn't found
     * @throws StaffIsntAssignedToServiceException if staff isn't assigned to service
     */
    void updateSchedule(ChangeDailyScheduleCommand command);
}