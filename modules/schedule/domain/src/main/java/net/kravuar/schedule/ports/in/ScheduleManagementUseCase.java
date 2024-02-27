package net.kravuar.schedule.ports.in;

import jakarta.validation.Valid;
import net.kravuar.context.AppValidated;
import net.kravuar.schedule.domain.commands.ChangeScheduleDurationCommand;
import net.kravuar.schedule.domain.commands.ChangeSchedulePatternsCommand;
import net.kravuar.schedule.domain.commands.CreateScheduleCommand;
import net.kravuar.schedule.domain.commands.RemoveScheduleCommand;
import net.kravuar.schedule.domain.exceptions.ScheduleNotFoundException;
import net.kravuar.schedule.domain.exceptions.ServiceNotFoundException;
import net.kravuar.schedule.domain.exceptions.StaffNotFoundException;

@AppValidated
public interface ScheduleManagementUseCase {
    /**
     * Update schedule.
     *
     * @param command command containing details of the update
     * @throws ScheduleNotFoundException if schedule wasn't found
     * @throws IllegalStateException if schedule duration isn't sufficient for provided patterns
     */
    void updateSchedulePatterns(@Valid ChangeSchedulePatternsCommand command);

    /**
     * Update schedule duration.
     *
     * @param command command containing details of the update
     * @throws ScheduleNotFoundException if schedule wasn't found
     * @throws IllegalStateException if schedule overlaps with other staff schedules
     */
    void updateScheduleDuration(@Valid ChangeScheduleDurationCommand command);

    /**
     * Add schedule for a staff member.
     *
     * @param command command containing details of the schedule creation
     * @throws StaffNotFoundException if staff wasn't found
     * @throws ServiceNotFoundException if service wasn't found
     */
    void addSchedule(@Valid CreateScheduleCommand command);

    /**
     * Removes schedule for a staff member.
     *
     * @param command command containing details of the removal
     * @throws ScheduleNotFoundException if schedule wasn't found
     */
    void removeSchedule(RemoveScheduleCommand command);
}