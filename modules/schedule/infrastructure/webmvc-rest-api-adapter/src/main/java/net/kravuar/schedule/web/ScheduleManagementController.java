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
    @PreAuthorize("isAuthenticated() && @authorizationHandler.isOwnerOfServiceBusiness(#command.serviceId(), authentication.details.subject)")
    ScheduleDTO createSchedule(@RequestBody CreateScheduleCommand command) {
        return dtoScheduleMapper.scheduleToDTO(
                scheduleManagementUseCase.createSchedule(command)
        );
    }

    @PutMapping("/change/patterns")
    @PreAuthorize("isAuthenticated() && @authorizationHandler.isOwnerOfScheduleBusiness(#command.scheduleId(), authentication.details.subject)")
    ScheduleDTO changePatterns(@RequestBody ChangeSchedulePatternsCommand command) {
        return dtoScheduleMapper.scheduleToDTO(
                scheduleManagementUseCase.changeSchedulePatterns(command)
        );
    }

    @PutMapping("/change/duration")
    @PreAuthorize("isAuthenticated() && @authorizationHandler.isOwnerOfScheduleBusiness(#command.scheduleId(), authentication.details.subject)")
    ScheduleDTO changeDuration(@RequestBody ChangeScheduleDurationCommand command) {
        return dtoScheduleMapper.scheduleToDTO(
                scheduleManagementUseCase.changeScheduleDuration(command)
        );
    }

    @PostMapping("/exception-days/create")
    @PreAuthorize("isAuthenticated() && @authorizationHandler.isOwnerOfServiceBusiness(#command.serviceId(), authentication.details.subject)")
    ScheduleExceptionDayDTO createExceptionDay(@RequestBody CreateScheduleExceptionDayCommand command) {
        return dtoScheduleExceptionDayMapper.scheduleExceptionDayToDTO(
                scheduleManagementUseCase.addScheduleExceptionDay(command)
        );
    }

    @PutMapping("/remove")
    @PreAuthorize("isAuthenticated() && @authorizationHandler.isOwnerOfScheduleBusiness(#command.scheduleId(), authentication.details.subject)")
    void removeSchedule(@RequestBody RemoveScheduleCommand command) {
        scheduleManagementUseCase.removeSchedule(command);
    }
}
