package net.kravuar.schedule.persistence.schedule;

import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.domain.Schedule;
import net.kravuar.schedule.domain.ScheduleExceptionDay;
import net.kravuar.schedule.ports.out.SchedulePersistencePort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JPASchedulePersistenceAdapter implements SchedulePersistencePort {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleExceptionDayRepository scheduleExceptionDayRepository;

    @Override
    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public ScheduleExceptionDay save(ScheduleExceptionDay scheduleExceptionDay) {
        return scheduleExceptionDayRepository.save(scheduleExceptionDay);
    }
}
