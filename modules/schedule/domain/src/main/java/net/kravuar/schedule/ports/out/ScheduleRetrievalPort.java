package net.kravuar.schedule.ports.out;

import net.kravuar.schedule.domain.Schedule;
import net.kravuar.schedule.domain.exceptions.ScheduleNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRetrievalPort {
    /**
     * Find active schedule by schedule id.
     *
     * @param scheduleId id of the schedule to find
     * @return active {@link Schedule} associated the with provided {@code scheduleId}
     * @throws ScheduleNotFoundException if schedule wasn't found
     */
    Schedule findActiveById(long scheduleId);

    /**
     * Find active schedules by staff.
     *
     * @param staffId id of the staff
     * @param from    lower bound
     * @param to      upper bound
     * @return {@link List<Schedule>} of schedules associated the with provided {@code staffId}
     */
    List<Schedule> findActiveByStaffId(long staffId, LocalDate from, LocalDate to);

    /**
     * Find active schedules by staff and service.
     *
     * @param staffId   id of the staff
     * @param serviceId id of the service
     * @param from      lower bound
     * @param to        upper bound
     * @return {@link List<Schedule>} of schedules associated the with provided {@code staffId} and {@code serviceId}
     */
    List<Schedule> findActiveByStaffIdAndServiceId(long staffId, long serviceId, LocalDate from, LocalDate to);
}
