package net.kravuar.schedule.persistence;

import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.domain.Schedule;
import net.kravuar.schedule.domain.ScheduleExceptionDay;
import net.kravuar.schedule.domain.Staff;
import net.kravuar.schedule.domain.exceptions.ScheduleNotFoundException;
import net.kravuar.schedule.ports.out.ScheduleRetrievalPort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JPAScheduleRetrievalAdapter implements ScheduleRetrievalPort {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleExceptionDayRepository exceptionDayRepository;

    @Override
    public Schedule findById(long exceptionId, boolean activeOnly) {
        return scheduleRepository.findById(exceptionId, activeOnly)
                .orElseThrow(ScheduleNotFoundException::new);
    }

    @Override
    public List<Schedule> findActiveSchedulesByStaffAndService(long staffId, long serviceId, LocalDate from, LocalDate to) {
        return scheduleRepository.findAllByStaffAndService(
                staffId,
                serviceId,
                from,
                to
        );
    }

    @Override
    public Map<Staff, List<Schedule>> findActiveSchedulesByService(long serviceId, LocalDate from, LocalDate to) {
        return scheduleRepository.findAllByService(
                serviceId,
                from,
                to
        ).stream().collect(Collectors.groupingBy(Schedule::getStaff));
    }

    @Override
    public Optional<ScheduleExceptionDay> findActiveExceptionDayByStaffAndService(long staffId, long serviceId, LocalDate date) {
        return exceptionDayRepository.findFullyActiveByStaffAndService(
                staffId,
                serviceId,
                date
        );
    }

    @Override
    public Map<LocalDate, ScheduleExceptionDay> findActiveExceptionDaysByStaffAndService(long staffId, long serviceId, LocalDate from, LocalDate to) {
        return exceptionDayRepository.findAllFullyActiveByStaffAndService(
                staffId,
                serviceId,
                from,
                to
        ).stream().collect(Collectors.toMap(
                ScheduleExceptionDay::getDate,
                Function.identity()
        ));
    }

    @Override
    public Map<Staff, Map<LocalDate, ScheduleExceptionDay>> findActiveExceptionDaysByService(long serviceId, LocalDate from, LocalDate to) {
        return exceptionDayRepository.findAllFullyActiveByService(
                serviceId,
                from,
                to
        ).stream().collect(Collectors.groupingBy(
                ScheduleExceptionDay::getStaff,
                Collectors.toMap(
                        ScheduleExceptionDay::getDate,
                        Function.identity()
                )
        ));
    }
}