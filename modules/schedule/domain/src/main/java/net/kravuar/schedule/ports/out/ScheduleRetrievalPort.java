package net.kravuar.schedule.ports.out;

import net.kravuar.schedule.domain.exceptions.ScheduleNotFoundException;
import net.kravuar.schedule.model.Schedule;
import net.kravuar.schedule.model.ScheduleExceptionDay;
import net.kravuar.schedule.model.Service;
import net.kravuar.schedule.model.Staff;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.Set;

public interface ScheduleRetrievalPort {
    /**
     * Find schedule by id.
     * Only with active parent entities (business, service and staff),
     * otherwise schedule should not be visible.
     *
     * @param scheduleId id of the schedule to find
     * @param activeOnly whether to search active schedule only
     * @return {@link Schedule} associated the with provided {@code scheduleId}
     * @throws ScheduleNotFoundException if schedule wasn't found
     */
    Schedule findById(long scheduleId, boolean activeOnly);

    /**
     * Find active schedules by staff and service in a date range.
     * Only with active parent entities (business, service and staff),
     * otherwise schedule should not be visible.
     * Find all that intersect with provided period
     *
     * @param staffId   id of the staff
     * @param serviceId id of the service
     * @param from      lower bound
     * @param to        upper bound
     * @return {@code List<Schedule>} of schedules associated the with provided {@code staffId} and {@code serviceId}
     */
    List<Schedule> findActiveSchedulesByStaffAndService(long staffId, long serviceId, LocalDate from, LocalDate to);

    /**
     * Find active schedules by staff and service which intersect with specified date.
     * Only with active parent entities (business, service and staff),
     * otherwise schedule should not be visible.
     *
     * @param staffId   id of the staff
     * @param serviceId id of the service
     * @param from      lower bound
     * @return {@code List<Schedule>} of schedules associated the with provided {@code staffId} and {@code serviceId}
     */
    List<Schedule> findActiveSchedulesByStaffAndService(long staffId, long serviceId, LocalDate from);

    /**
     * Find active schedules of staff members of service.
     * Only with active parent entities (business, service and staff),
     * otherwise schedule day should not be visible.
     * Find all that intersect with provided period
     *
     * @param serviceId id of the service
     * @param from      lower bound
     * @param to        upper bound
     * @return {@code Map<Staff, List<Schedule>>} of schedules for each staff associated the with provided {@code serviceId}
     */
    Map<Staff, List<Schedule>> findActiveSchedulesByService(long serviceId, LocalDate from, LocalDate to);

    /**
     * Find active schedules of staff members of services.
     * Only with active parent entities (business, service and staff),
     * otherwise schedule should not be visible.
     * Find all that intersect with provided period
     *
     * @param serviceIds ids of the services
     * @param from      lower bound
     * @param to        upper bound
     * @return {@code Map<Service, Map<Staff, List<Schedule>>>} of schedules for each staff for each service associated with provided id
     */
    Map<Service, Map<Staff, List<Schedule>>> findActiveSchedulesByServices(Set<Long> serviceIds, LocalDate from, LocalDate to);

    /**
     * Find schedule exception day by staff, service and date.
     * Only with active parent entities (business, service and staff),
     * otherwise schedule exception day should not be visible.
     *
     * @param serviceId id of the service
     * @param staffId   id of the staff
     * @param date      exception day date
     * @return {@code Optional<ScheduleExceptionDay>} search result
     * associated the with provided {@code staffId}, {@code serviceId} and {@code date},
     */
    Optional<ScheduleExceptionDay> findActiveExceptionDayByStaffAndService(long staffId, long serviceId, LocalDate date);

    /**
     * Find schedule exception days by staff and service.
     * Only with active parent entities (business, service and staff),
     * otherwise schedule exception day should not be visible.
     *
     * @param staffId   id of the staff
     * @param serviceId id of the service
     * @param from      lower bound
     * @param to        upper bound (inclusive)
     * @return {@code Map<LocalDate, ScheduleExceptionDay>} of schedule exception days
     * associated the with provided {@code staffId} and {@code serviceId},
     */
    NavigableMap<LocalDate, ScheduleExceptionDay> findActiveExceptionDaysByStaffAndService(long staffId, long serviceId, LocalDate from, LocalDate to);

    /**
     * Find schedule exception days by service.
     * Only with active parent entities (business, service and staff),
     * otherwise schedule exception day should not be visible.
     *
     * @param serviceId id of the service
     * @param from      lower bound
     * @param to        upper bound (inclusive)
     * @return {@code Map<Staff, Map<LocalDate, ScheduleExceptionDay>>} of schedule exception days for each staff of provided service
     * associated the with provided {@code serviceId},
     */
    Map<Staff, NavigableMap<LocalDate, ScheduleExceptionDay>> findActiveExceptionDaysByService(long serviceId, LocalDate from, LocalDate to);

    /**
     * Find schedule exception days by services.
     * Only with active parent entities (business, service and staff),
     * otherwise schedule exception day should not be visible.
     *
     * @param serviceIds ids of the services
     * @param from      lower bound
     * @param to        upper bound (inclusive)
     * @return {@code Map<Service, Map<Staff, NavigableMap<LocalDate, ScheduleExceptionDay>>>} of schedule exception days for each staff for each service
     * associated the with provided {@code serviceId},
     */
    Map<Service, Map<Staff, NavigableMap<LocalDate, ScheduleExceptionDay>>> findActiveExceptionDaysByServices(Set<Long> serviceIds, LocalDate from, LocalDate to);
}
