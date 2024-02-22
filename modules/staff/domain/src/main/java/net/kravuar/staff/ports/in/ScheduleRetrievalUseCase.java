package net.kravuar.staff.ports.in;

import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.domain.StaffSchedule;
import net.kravuar.staff.domain.commands.ServiceScheduleRetrievalCommand;
import net.kravuar.staff.domain.commands.StaffScheduleRetrievalCommand;
import net.kravuar.staff.domain.exceptions.ServiceNotFoundException;
import net.kravuar.staff.domain.exceptions.StaffIsntAssignedToServiceException;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Map;

public interface ScheduleRetrievalUseCase {
    /**
     * Retrieve all schedules (with changes) for a staff member starting from provided date.
     *
     * @param command command containing information for schedules retrieval
     * @return {@code Map<LocalDateTime, Map<DayOfWeek, StaffSchedule>>} where key is schedule change datetime and value is schedule map (day of the week to schedule)
     * @throws StaffIsntAssignedToServiceException if staff isn't assigned to requested service
     * @throws ServiceNotFoundException if service wasn't found
     */
    Map<LocalDateTime, Map<DayOfWeek, StaffSchedule>> findSchedulesByDateServiceAndStaff(StaffScheduleRetrievalCommand command);

    /**
     * Retrieve all schedules for all staff members of a service by date.
     *
     * @param command command containing information for schedules retrieval
     * @return {@code Map<Staff, Map<LocalDateTime, Map<DayOfWeek, StaffWorkingHour>>>} where key is {@link Staff} and value is map with key as schedule change datetime and value is schedule map (day of the week to schedule)
     * @throws ServiceNotFoundException if service wasn't found
     */
    Map<Staff, Map<LocalDateTime, Map<DayOfWeek, StaffSchedule>>> findSchedulesByDateAndService(ServiceScheduleRetrievalCommand command);
}