package net.kravuar.schedule.persistence;

import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.domain.Schedule;
import net.kravuar.schedule.ports.out.SchedulePersistencePort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JPASchedulePersistenceAdapter implements SchedulePersistencePort {
    private final ScheduleRepository scheduleRepository;

    @Override
    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }
}
