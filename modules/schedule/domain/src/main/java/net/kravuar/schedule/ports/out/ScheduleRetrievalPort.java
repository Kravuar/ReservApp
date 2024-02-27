package net.kravuar.schedule.ports.out;

import net.kravuar.schedule.domain.Schedule;
import net.kravuar.schedule.domain.exceptions.ScheduleNotFoundException;

import java.util.List;

public interface ScheduleRetrievalPort {
    /**
     * Find schedule by scheduleId.
     *
     * @param scheduleId id of the schedule to find
     * @return {@link Schedule} associated the with provided {@code scheduleId}
     * @throws ScheduleNotFoundException if schedule wasn't found
     */
    Schedule findById(long scheduleId);

    /**
     * Find active schedule by scheduleId.
     *
     * @param scheduleId id of the schedule to find
     * @return active {@link Schedule} associated the with provided {@code scheduleId}
     * @throws ScheduleNotFoundException if schedule wasn't found
     */
    Schedule findActiveById(long scheduleId);

    /**
     * Find schedules by staffId.
     *
     * @param staffId id of the staff
     * @return {@link List<Schedule>} of schedules associated the with provided {@code staffId}
     */
    List<Schedule> findByStaffId(long staffId);
}
