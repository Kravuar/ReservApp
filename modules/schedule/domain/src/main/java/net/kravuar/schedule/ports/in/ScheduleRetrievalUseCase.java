package net.kravuar.schedule.ports.in;

import net.kravuar.schedule.domain.Staff;
import net.kravuar.schedule.domain.commands.RetrieveScheduleByServiceCommand;
import net.kravuar.schedule.domain.commands.RetrieveScheduleByStaffAndServiceCommand;
import net.kravuar.schedule.domain.halfbreeddomain.WorkingHours;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ScheduleRetrievalUseCase {
    /**
     * Find active schedule for a staff member and service.
     *
     * @param command command containing details of the schedule retrieval
     */
    Map<LocalDate, List<WorkingHours>> findActiveScheduleByStaffAndService(RetrieveScheduleByStaffAndServiceCommand command);

    /**
     * Find active schedule for a service.
     *
     * @param command command containing details of the schedule retrieval
     */
    Map<Staff, Map<LocalDate, List<WorkingHours>>> findActiveScheduleByService(RetrieveScheduleByServiceCommand command);
}
