package net.kravuar.staff;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.staff.domain.DailySchedule;
import net.kravuar.staff.domain.Service;
import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.domain.commands.ServiceScheduleRetrievalCommand;
import net.kravuar.staff.domain.commands.StaffScheduleRetrievalCommand;
import net.kravuar.staff.domain.exceptions.ServiceDisabledException;
import net.kravuar.staff.ports.in.ScheduleRetrievalUseCase;
import net.kravuar.staff.ports.out.ScheduleRetrievalPort;
import net.kravuar.staff.ports.out.ServiceRetrievalPort;
import net.kravuar.staff.ports.out.StaffRetrievalPort;

import java.time.DayOfWeek;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@AppComponent
@RequiredArgsConstructor
public class ScheduleRetrievalFacade implements ScheduleRetrievalUseCase {
    private final ScheduleRetrievalPort scheduleRetrievalPort;
    private final ServiceRetrievalPort serviceRetrievalPort;
    private final StaffRetrievalPort staffRetrievalPort;

    @Override
    public Map<DayOfWeek, SortedSet<DailySchedule>> findScheduleWithChangesByStaff(StaffScheduleRetrievalCommand command) {
        Service service = serviceRetrievalPort.findById(command.serviceId());
        Staff staff = staffRetrievalPort.findById(command.staffId());

        if (!service.isActive())
            throw new ServiceDisabledException();

        var scheduleChanges = scheduleRetrievalPort.findDailyScheduleChanges(
                service,
                staff,
                command.startDate()
        );

        // Mapping day of the week to the sorted set of schedules, containing only the latest update for each validFrom date
        return scheduleChanges.stream()
                .collect(groupByDayWithLatestUpdatesOnly());
    }

    @Override
    public Map<Staff, Map<DayOfWeek, SortedSet<DailySchedule>>> findScheduleWithChangesByService(ServiceScheduleRetrievalCommand command) {
        Service service = serviceRetrievalPort.findById(command.serviceId());

        if (!service.isActive())
            throw new ServiceDisabledException();

        var scheduleChangesForAllStaff = scheduleRetrievalPort.findDailyScheduleChangesByDateAndService(
                service,
                command.startDate()
        );

        // Mapping staff to map of day of the week to the sorted set of schedules, containing only the latest update for each validFrom date
        return scheduleChangesForAllStaff.entrySet().stream().collect(
                Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .collect(groupByDayWithLatestUpdatesOnly())
                )
        );
    }

    private Collector<DailySchedule, ?, Map<DayOfWeek, SortedSet<DailySchedule>>> groupByDayWithLatestUpdatesOnly() {
        return Collectors.groupingBy(
                DailySchedule::getDayOfWeek,
                Collectors.collectingAndThen(
                        Collectors.toMap(
                                DailySchedule::getValidFrom,
                                Function.identity(),
                                BinaryOperator.maxBy(Comparator.comparing(DailySchedule::getCreatedAt))
                        ),
                        map -> new TreeSet<>(map.values())
                )
        );
    }
}
