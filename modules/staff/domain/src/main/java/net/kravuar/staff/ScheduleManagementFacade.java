package net.kravuar.staff;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.staff.domain.DailySchedule;
import net.kravuar.staff.domain.WorkingHoursFragment;
import net.kravuar.staff.domain.commands.ChangeDailyScheduleCommand;
import net.kravuar.staff.ports.in.ScheduleManagementUseCase;
import net.kravuar.staff.ports.out.SchedulePersistencePort;

import java.util.TreeSet;
import java.util.stream.Collectors;

@AppComponent
@RequiredArgsConstructor
public class ScheduleManagementFacade implements ScheduleManagementUseCase {
    private final SchedulePersistencePort schedulePersistencePort;

    @Override
    public void updateSchedule(ChangeDailyScheduleCommand command) {
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
        schedulePersistencePort.saveStaffScheduleChange(schedule);
    }
}
