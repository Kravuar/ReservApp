package net.kravuar.schedule.ports.in;

import net.kravuar.schedule.domain.Schedule;
import net.kravuar.schedule.domain.ScheduleExceptionDay;
import net.kravuar.schedule.domain.Staff;
import net.kravuar.schedule.domain.commands.RetrieveScheduleByServiceCommand;
import net.kravuar.schedule.domain.commands.RetrieveScheduleByStaffAndServiceCommand;
import net.kravuar.schedule.domain.commands.RetrieveScheduleExceptionDaysByStaffAndServiceCommand;
import net.kravuar.schedule.domain.exceptions.ScheduleNotFoundException;
import net.kravuar.schedule.domain.weak.WorkingHours;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ScheduleRetrievalUseCase {
    /**
     * Find schedule by id.
     *
     * @param scheduleId id of the schedule
     * @param activeOnly whether to search activeOnly
     * @return {@link Schedule} associated the with provided {@code scheduleId}
     * @throws ScheduleNotFoundException if schedule wasn't found
     */
    Schedule findScheduleById(long scheduleId, boolean activeOnly);

    /**
     * Find active schedules by staff and service.
     *
     * @param staffId id of the staff
     * @param serviceId id of the service
     * @return {@code List<Schedule>} of schedules associated the with provided {@code staffId} and {@code serviceId}
     * @throws ScheduleNotFoundException if schedule wasn't found
     */
    List<Schedule> findActiveSchedulesByStaffAndService(long staffId, long serviceId);

    /**
     * Find active schedule for a staff member and service in per day format.
     *
     * @param command command containing details of the schedule retrieval
     * @return {@code Map<LocalDate, List<WorkingHours>>} mapping date to working hours
     */
    Map<LocalDate, List<WorkingHours>> findActiveScheduleByStaffAndServiceInPerDay(RetrieveScheduleByStaffAndServiceCommand command);

    /**
     * Find active schedule for a service in per day format.
     *
     * @param command command containing details of the schedule retrieval
     * @return {@code Map<Staff, Map<LocalDate, List<WorkingHours>>>} mapping date to working hours for each staff
     */
    Map<Staff, Map<LocalDate, List<WorkingHours>>> findActiveScheduleByServiceInPerDay(RetrieveScheduleByServiceCommand command);

    /**
     * Find schedule exception days by staff and service.
     *
     * @param command command containing details of the schedule retrieval
     * @return {@code Map<LocalDate, ScheduleExceptionDay>} mapping date to schedule exception day information
     */
    Map<LocalDate, ScheduleExceptionDay> findActiveExceptionDaysByStaffAndService(RetrieveScheduleExceptionDaysByStaffAndServiceCommand command);
}
