package net.kravuar.schedule.ports.in;

import jakarta.validation.Valid;
import net.kravuar.context.AppValidated;
import net.kravuar.schedule.domain.commands.*;
import net.kravuar.schedule.domain.exceptions.ScheduleNotFoundException;
import net.kravuar.schedule.domain.exceptions.ServiceNotFoundException;
import net.kravuar.schedule.domain.exceptions.StaffNotFoundException;
import net.kravuar.schedule.model.Schedule;
import net.kravuar.schedule.model.ScheduleExceptionDay;

@AppValidated
public interface ScheduleManagementUseCase {
    /**
     * Change schedule patterns.
     *
     * @param command command containing details of the update
     * @throws ScheduleNotFoundException if schedule wasn't found
     * @throws IllegalStateException     if schedule duration isn't sufficient for provided patterns
     */
    Schedule changeSchedulePatterns(@Valid ChangeSchedulePatternsCommand command);

    /**
     * Change schedule duration.
     *
     * @param command command containing details of the update
     * @throws ScheduleNotFoundException if schedule wasn't found
     * @throws IllegalStateException     if schedule overlaps with other staff schedules or
     *                                   does not have sufficient duration for provided patterns
     */
    Schedule changeScheduleDuration(@Valid ChangeScheduleDurationCommand command);

    /**
     * Create schedule for a staff member.
     *
     * @param command command containing details of the schedule creation
     * @throws StaffNotFoundException   if staff wasn't found
     * @throws ServiceNotFoundException if service wasn't found
     * @throws IllegalStateException    if schedule overlaps with other staff schedules or
     *                                  does not have sufficient duration for provided patterns
     */
    Schedule createSchedule(@Valid CreateScheduleCommand command);

    /**
     * Create or update schedule exception day for a staff member and service.
     *
     * @param command command containing details of the schedule exception day creation
     * @throws StaffNotFoundException   if staff wasn't found
     * @throws ServiceNotFoundException if service wasn't found
     */
    ScheduleExceptionDay addOrUpdateScheduleExceptionDay(@Valid CreateScheduleExceptionDayCommand command);

    /**
     * Removes schedule for a staff member.
     *
     * @param command command containing details of the removal
     * @throws ScheduleNotFoundException if schedule wasn't found
     */
    void removeSchedule(RemoveScheduleCommand command);
}