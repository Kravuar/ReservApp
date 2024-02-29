package net.kravuar.schedule;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.schedule.domain.Schedule;
import net.kravuar.schedule.domain.Staff;
import net.kravuar.schedule.domain.commands.RetrieveScheduleByServiceCommand;
import net.kravuar.schedule.domain.commands.RetrieveScheduleByStaffAndServiceCommand;
import net.kravuar.schedule.domain.halfbreeddomain.WorkingHours;
import net.kravuar.schedule.ports.in.ScheduleRetrievalUseCase;
import net.kravuar.schedule.ports.out.ScheduleRetrievalPort;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AppComponent
@RequiredArgsConstructor
public class ScheduleRetrievalFacade implements ScheduleRetrievalUseCase {
    private final ScheduleRetrievalPort scheduleRetrievalPort;

    @Override
    public Map<LocalDate, List<WorkingHours>> findActiveScheduleByStaffAndService(RetrieveScheduleByStaffAndServiceCommand command) {
        List<Schedule> schedules = scheduleRetrievalPort.findActiveByStaffIdAndServiceId(command.staffId(), command.serviceId(), command.from(), command.to());

        Map<LocalDate, List<WorkingHours>> perDaySchedule = new HashMap<>();
        int daysFromBeginning = 0;
        for (LocalDate currentDay = command.from(); currentDay.isBefore(command.to()); currentDay = currentDay.plusDays(1)) {

        }
    }

    @Override
    public Map<Staff, Map<LocalDate, List<WorkingHours>>> findActiveScheduleByService(RetrieveScheduleByServiceCommand command) {
        List<Schedule> schedules = scheduleRetrievalPort.findActiveByStaffId(command.serviceId(), command.from(), command.to());
    }
}
