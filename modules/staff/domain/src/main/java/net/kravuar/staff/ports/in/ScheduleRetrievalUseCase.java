package net.kravuar.staff.ports.in;

import net.kravuar.staff.domain.DailySchedule;
import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.domain.commands.ServiceScheduleRetrievalCommand;
import net.kravuar.staff.domain.commands.StaffScheduleRetrievalCommand;
import net.kravuar.staff.domain.exceptions.ServiceDisabledException;
import net.kravuar.staff.domain.exceptions.ServiceNotFoundException;
import net.kravuar.staff.domain.exceptions.StaffNotFoundException;

import java.util.Map;
import java.util.SortedSet;

public interface ScheduleRetrievalUseCase {
    /**
     * Retrieve all daily schedule's (with changes) for a specific service, staff, and starting date.
     *
     * @param command command containing information for schedules retrieval
     * @return sorted collection of daily schedule changes
     * @throws ServiceNotFoundException if the service wasn't found
     * @throws StaffNotFoundException if the staff wasn't found
     * @throws ServiceDisabledException if the service is disabled
     */
    SortedSet<DailySchedule> findDailyScheduleChangesByStaff(StaffScheduleRetrievalCommand command);

    /**
     * Retrieve all daily schedule's (with changes) for all staff members of a service by starting date.
     *
     * @param command command containing information for schedules retrieval
     * @return map of staff to sorted daily schedule changes
     * @throws ServiceNotFoundException if the service wasn't found
     * @throws ServiceDisabledException if the service is disabled
     */
    Map<Staff, SortedSet<DailySchedule>> findDailyScheduleChangesByService(ServiceScheduleRetrievalCommand command);
}
