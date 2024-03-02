package net.kravuar.schedule.persistence;

import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.domain.Schedule;
import net.kravuar.schedule.domain.Staff;
import net.kravuar.schedule.domain.exceptions.ScheduleNotFoundException;
import net.kravuar.schedule.ports.out.ScheduleRetrievalPort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JPAScheduleRetrievalAdapter implements ScheduleRetrievalPort {
    private final ScheduleRepository scheduleRepository;

    @Override
    public Schedule findById(long scheduleId, boolean activeOnly) {
        return scheduleRepository.findByIdAndActive(scheduleId, activeOnly)
                .orElseThrow(ScheduleNotFoundException::new);
    }

    @Override
    public List<Schedule> findActiveByStaffId(long staffId, LocalDate from, LocalDate to) {
        return scheduleRepository.findAllByStaffIdAndDateBetween(
                staffId,
                from,
                to
        );
    }

    @Override
    public List<Schedule> findActiveByStaffIdAndServiceId(long staffId, long serviceId, LocalDate from, LocalDate to) {
        return scheduleRepository.findAllByStaffIdAndServiceIdAndDateBetween(
                staffId,
                serviceId,
                from,
                to
        );
    }

    @Override
    public Map<Staff, List<Schedule>> findActiveByServiceId(long serviceId, LocalDate from, LocalDate to) {
        return scheduleRepository.findAllByServiceIdAndDateBetween(
                serviceId,
                from,
                to
        ).stream().collect(Collectors.groupingBy(Schedule::getStaff));
    }
}
