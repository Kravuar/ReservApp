package net.kravuar.staff;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.staff.domain.DailySchedule;
import net.kravuar.staff.domain.Service;
import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.domain.WorkingHoursFragment;
import net.kravuar.staff.domain.commands.ChangeDailyScheduleCommand;
import net.kravuar.staff.ports.in.ScheduleManagementUseCase;
import net.kravuar.staff.ports.out.*;

import java.util.TreeSet;
import java.util.stream.Collectors;

@AppComponent
@RequiredArgsConstructor
public class ScheduleManagementFacade implements ScheduleManagementUseCase {
    private final SchedulePersistencePort schedulePersistencePort;
    private final ScheduleNotificationPort scheduleNotificationPort;
    private final ServiceRetrievalPort serviceRetrievalPort;
    private final StaffRetrievalPort staffRetrievalPort;
    private final ScheduleLockPort scheduleLockPort;

    @Override
    public void updateSchedule(ChangeDailyScheduleCommand command) {
        try {
            Service service = serviceRetrievalPort.findById(command.getServiceId());
            Staff staff = staffRetrievalPort.findStaffById(command.getStaffId());

            scheduleLockPort.lock(command.getServiceId(), command.getStaffId(), true);

            DailySchedule schedule = DailySchedule.builder()
                    .dayOfWeek(command.getDayOfWeek())
                    .validFrom(command.getValidFrom())
                    .workingHours(command.getWorkingHours().stream()
                            .map(dto -> WorkingHoursFragment.builder()
                                    .start(dto.start())
                                    .end(dto.end())
                                    .cost(dto.cost())
                                    .build())
                            .collect(Collectors.toCollection(TreeSet::new))
                    ).build();
            schedulePersistencePort.saveStaffScheduleChange(service, staff, schedule);
            scheduleNotificationPort.notifyScheduleChange(schedule);
        } finally {
            scheduleLockPort.lock(command.getServiceId(), command.getStaffId(), false);
        }
    }
}
