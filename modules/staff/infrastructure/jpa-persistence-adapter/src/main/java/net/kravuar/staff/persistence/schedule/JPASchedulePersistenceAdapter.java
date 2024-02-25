package net.kravuar.staff.persistence.schedule;

import lombok.RequiredArgsConstructor;
import net.kravuar.staff.domain.DailySchedule;
import net.kravuar.staff.domain.Service;
import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.ports.out.SchedulePersistencePort;
import org.springframework.stereotype.Component;

import java.util.TreeSet;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class JPASchedulePersistenceAdapter implements SchedulePersistencePort {
    private final ScheduleRepository scheduleRepository;
    private final WorkingHoursMapper workingHoursMapper;

    @Override
    public void saveStaffScheduleChange(Service service, Staff staff, DailySchedule schedule) {
        ScheduleModel scheduleModel = ScheduleModel.builder()
                .dayOfWeek(schedule.getDayOfWeek())
                .service(service)
                .staff(staff)
                .validFrom(schedule.getValidFrom())
                .disabledAt(schedule.getDisabledAt())
                .workingHours(schedule.getWorkingHours().stream()
                        .map(workingHoursMapper::toModel)
                        .collect(Collectors.toCollection(TreeSet::new))
                )
                .build();
        scheduleRepository.save(scheduleModel);
    }
}
