package net.kravuar.schedule.ports.out;

import net.kravuar.schedule.domain.Schedule;
import net.kravuar.schedule.domain.Staff;
import net.kravuar.schedule.domain.exceptions.ScheduleNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ScheduleRetrievalPort {
    /**
     * Find schedule by schedule id.
     *
     * @param scheduleId id of the schedule to find
     * @param activeOnly whether to search activeOnly
     * @return {@link Schedule} associated the with provided {@code scheduleId}
     * @throws ScheduleNotFoundException if schedule wasn't found
     */
    Schedule findById(long scheduleId, boolean activeOnly);

    /**
     * Find active schedules by staff.
     * Find all that have at least 1 bound between {@code from} and {@code to},
     * or both bounds out of the range.
     *
     * @param staffId id of the staff
     * @param from    lower bound
     * @param to      upper bound
     * @return {@code List<Schedule>} of schedules associated the with provided {@code staffId}
     */
    List<Schedule> findActiveByStaffId(long staffId, LocalDate from, LocalDate to);

    /**
     * Find active schedules by staff and service.
     * Find all that have at least 1 bound between {@code from} and {@code to},
     * or both bounds out of the range.
     *
     * @param staffId   id of the staff
     * @param serviceId id of the service
     * @param from      lower bound
     * @param to        upper bound
     * @return {@code List<Schedule>} of schedules associated the with provided {@code staffId} and {@code serviceId}
     */
    List<Schedule> findActiveByStaffIdAndServiceId(long staffId, long serviceId, LocalDate from, LocalDate to);

    /**
     * Find active schedules by service.
     * Find all that have at least 1 bound between {@code from} and {@code to},
     * or both bounds out of the range.
     *
     * @param serviceId id of the service
     * @param from      lower bound
     * @param to        upper bound
     * @return {@code Map<Staff, List<Schedule>>} of schedules for each staff associated the with provided @code serviceId}
     */
    Map<Staff, List<Schedule>> findActiveByServiceId(long serviceId, LocalDate from, LocalDate to);
}
