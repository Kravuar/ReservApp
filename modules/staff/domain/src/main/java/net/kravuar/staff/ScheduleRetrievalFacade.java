package net.kravuar.staff;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.staff.domain.DailySchedule;
import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.domain.commands.ServiceScheduleRetrievalCommand;
import net.kravuar.staff.domain.commands.StaffScheduleRetrievalCommand;
import net.kravuar.staff.ports.in.ScheduleRetrievalUseCase;
import net.kravuar.staff.ports.out.ScheduleRetrievalPort;

import java.util.Map;
import java.util.SortedSet;

@AppComponent
@RequiredArgsConstructor
public class ScheduleRetrievalFacade implements ScheduleRetrievalUseCase {
    private final ScheduleRetrievalPort scheduleRetrievalPort;

    @Override
    public SortedSet<DailySchedule> findDailyScheduleChangesByStaff(StaffScheduleRetrievalCommand command) {
        return scheduleRetrievalPort.findDailyScheduleChanges(
                command.serviceId(),
                command.stuffId(),
                command.startDate()
        );
    }

    @Override
    public Map<Staff, SortedSet<DailySchedule>> findDailyScheduleChangesByService(ServiceScheduleRetrievalCommand command) {
        return scheduleRetrievalPort.findDailyScheduleChangesByDateAndService(
                command.serviceId(),
                command.startDate()
        );
    }
}
