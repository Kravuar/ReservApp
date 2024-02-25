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

import java.util.Map;
import java.util.SortedSet;

@AppComponent
@RequiredArgsConstructor
public class ScheduleRetrievalFacade implements ScheduleRetrievalUseCase {
    private final ScheduleRetrievalPort scheduleRetrievalPort;
    private final ServiceRetrievalPort serviceRetrievalPort;
    private final StaffRetrievalPort staffRetrievalPort;

    @Override
    public SortedSet<DailySchedule> findDailyScheduleChangesByStaff(StaffScheduleRetrievalCommand command) {
        Service service = serviceRetrievalPort.findById(command.serviceId());
        Staff staff = staffRetrievalPort.findById(command.staffId());

        if (!service.isActive())
            throw new ServiceDisabledException();

        return scheduleRetrievalPort.findDailyScheduleChanges(
                service,
                staff,
                command.startDate()
        );
    }

    @Override
    public Map<Staff, SortedSet<DailySchedule>> findDailyScheduleChangesByService(ServiceScheduleRetrievalCommand command) {
        Service service = serviceRetrievalPort.findById(command.serviceId());

        if (!service.isActive())
            throw new ServiceDisabledException();

        return scheduleRetrievalPort.findDailyScheduleChangesByDateAndService(
                service,
                command.startDate()
        );
    }
}
