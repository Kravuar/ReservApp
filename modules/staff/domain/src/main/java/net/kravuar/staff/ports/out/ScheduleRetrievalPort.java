package net.kravuar.staff.ports.out;

import net.kravuar.staff.domain.DailySchedule;
import net.kravuar.staff.domain.Service;
import net.kravuar.staff.domain.Staff;

import java.time.LocalDate;
import java.util.Map;
import java.util.SortedSet;

public interface ScheduleRetrievalPort {
    /**
     * Retrieve all daily schedule's (with changes) for a specific service, staff, and starting date.
     *
     * @param service      the service for which schedules are retrieved
     * @param staff        the staff member for which schedules are retrieved
     * @param startingFrom the starting date from which schedules are retrieved
     * @return sorted collection of daily schedule changes
     */
    SortedSet<DailySchedule> findDailyScheduleChanges(Service service, Staff staff, LocalDate startingFrom);

    /**
     * Retrieve all daily schedule's (with changes) for all staff members of a service by starting date.
     *
     * @param service the service for which schedules are retrieved
     * @param startingFrom the starting date from which schedules are retrieved
     * @return map of staff to sorted daily schedule changes
     */
    Map<Staff, SortedSet<DailySchedule>> findDailyScheduleChangesByDateAndService(Service service, LocalDate startingFrom);
}
