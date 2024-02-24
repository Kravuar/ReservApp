package net.kravuar.staff.ports.out;

import net.kravuar.staff.domain.DailySchedule;
import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.domain.exceptions.ServiceNotFoundException;
import net.kravuar.staff.domain.exceptions.StaffIsntAssignedToServiceException;

import java.time.LocalDate;
import java.util.Map;
import java.util.SortedSet;

public interface ScheduleRetrievalPort {
    /**
     * Retrieve all daily schedule's (with changes) for a specific service, staff, and starting date.
     *
     * @param serviceId the service for which schedules are retrieved
     * @param staffId the staff member for which schedules are retrieved
     * @param startingFrom the starting date from which schedules are retrieved
     * @return sorted collection of daily schedule changes
     * @throws StaffIsntAssignedToServiceException if staff isn't assigned to the requested service
     * @throws ServiceNotFoundException if the service wasn't found
     */
    SortedSet<DailySchedule> findDailyScheduleChanges(long serviceId, long staffId, LocalDate startingFrom);

    /**
     * Retrieve all daily schedule's (with changes) for all staff members of a service by starting date.
     *
     * @param serviceId the service for which schedules are retrieved
     * @param startingFrom the starting date from which schedules are retrieved
     * @return map of staff to sorted daily schedule changes
     * @throws ServiceNotFoundException if the service wasn't found
     */
    Map<Staff, SortedSet<DailySchedule>> findDailyScheduleChangesByDateAndService(long serviceId, LocalDate startingFrom);
}
