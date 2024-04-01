package net.kravuar.schedule.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.domain.commands.*;
import net.kravuar.schedule.ports.in.ScheduleManagementUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/management")
@RequiredArgsConstructor
class ScheduleManagementController {
    private final ScheduleManagementUseCase scheduleManagementUseCase;
    private final DTOScheduleMapper dtoScheduleMapper;
    private final DTOScheduleExceptionDayMapper dtoScheduleExceptionDayMapper;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated() && hasPermission(#command.serviceId(), 'Schedule', 'Create')")
    ScheduleDTO createSchedule(@RequestBody CreateScheduleCommand command) {
        return dtoScheduleMapper.scheduleToDTO(
                scheduleManagementUseCase.createSchedule(command)
        );
    }

    @PutMapping("/change/patterns")
    @PreAuthorize("isAuthenticated() && hasPermission(#command.scheduleId(), 'Schedule', 'Update')")
    ScheduleDTO changePatterns(@RequestBody ChangeSchedulePatternsCommand command) {
        return dtoScheduleMapper.scheduleToDTO(
                scheduleManagementUseCase.changeSchedulePatterns(command)
        );
    }

    @PutMapping("/change/duration")
    @PreAuthorize("isAuthenticated() && hasPermission(#command.scheduleId(), 'Schedule', 'Update')")
    ScheduleDTO changeDuration(@RequestBody ChangeScheduleDurationCommand command) {
        return dtoScheduleMapper.scheduleToDTO(
                scheduleManagementUseCase.changeScheduleDuration(command)
        );
    }

    @PostMapping("/exception-days/create")
    @PreAuthorize("isAuthenticated() && hasPermission(#command.serviceId(), 'ScheduleException', 'Create')")
    ScheduleExceptionDayDTO createExceptionDay(@RequestBody CreateScheduleExceptionDayCommand command) {
        return dtoScheduleExceptionDayMapper.scheduleExceptionDayToDTO(
                scheduleManagementUseCase.addOrUpdateScheduleExceptionDay(command)
        );
    }

    @PutMapping("/remove")
    @PreAuthorize("isAuthenticated() && hasPermission(#command.scheduleId(), 'Schedule', 'Delete')")
    void removeSchedule(@RequestBody RemoveScheduleCommand command) {
        scheduleManagementUseCase.removeSchedule(command);
    }
}
