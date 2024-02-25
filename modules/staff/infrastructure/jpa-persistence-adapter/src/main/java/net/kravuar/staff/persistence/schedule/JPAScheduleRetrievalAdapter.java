package net.kravuar.staff.persistence.schedule;

import lombok.RequiredArgsConstructor;
import net.kravuar.staff.domain.DailySchedule;
import net.kravuar.staff.domain.Service;
import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.ports.out.ScheduleRetrievalPort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class JPAScheduleRetrievalAdapter implements ScheduleRetrievalPort {
    private final ScheduleRepository scheduleRepository;
    private final WorkingHoursMapper workingHoursMapper;


    @Override
    public SortedSet<DailySchedule> findDailyScheduleChanges(Service service, Staff staff, LocalDate startingFrom) {
        List<ScheduleModel> scheduleModels = scheduleRepository.findAllByServiceAndStaffAndValidFromAfter(service, staff, startingFrom);
        return scheduleModels.stream().map(schedule ->
                DailySchedule.builder()
                        .id(schedule.getId())
                        .validFrom(schedule.getValidFrom())
                        .disabledAt(schedule.getDisabledAt())
                        .dayOfWeek(schedule.getDayOfWeek())
                        .workingHours(schedule.getWorkingHours()
                                .stream()
                                .map(workingHoursMapper::toDomain)
                                .collect(Collectors.toCollection(TreeSet::new))
                        )
                        .build()
        ).collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public Map<Staff, SortedSet<DailySchedule>> findDailyScheduleChangesByDateAndService(Service service, LocalDate startingFrom) {
        List<ScheduleModel> scheduleModels = scheduleRepository.findAllByServiceAndValidFromAfter(service, startingFrom);
        return scheduleModels.stream().collect(Collectors.groupingBy(
                        ScheduleModel::getStaff,
                        Collectors.mapping(
                                schedule -> DailySchedule.builder()
                                        .id(schedule.getId())
                                        .validFrom(schedule.getValidFrom())
                                        .disabledAt(schedule.getDisabledAt())
                                        .dayOfWeek(schedule.getDayOfWeek())
                                        .workingHours(schedule.getWorkingHours()
                                                .stream()
                                                .map(workingHoursMapper::toDomain)
                                                .collect(Collectors.toCollection(TreeSet::new))
                                        )
                                        .build(),
                                Collectors.toCollection(TreeSet::new)
                        )
                )
        );
    }
}
