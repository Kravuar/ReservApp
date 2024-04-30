package net.kravuar.schedule.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.domain.commands.*;
import net.kravuar.schedule.dto.*;
import net.kravuar.schedule.ports.in.ScheduleManagementUseCase;
import net.kravuar.staff.model.SchedulePattern;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/management")
@RequiredArgsConstructor
class ScheduleManagementController {
    private final ScheduleManagementUseCase scheduleManagementUseCase;
    private final DTOScheduleMapper dtoScheduleMapper;
    private final DTOScheduleExceptionDayMapper dtoScheduleExceptionDayMapper;

    @PostMapping("/create/{serviceId}/{staffId}")
    @PreAuthorize("hasPermission(#serviceId, 'Schedule', 'Create')")
    ScheduleDTO createSchedule(@PathVariable("serviceId") long serviceId, @PathVariable("staffId") long staffId, @RequestBody ScheduleDetailsDTO details) {
        return dtoScheduleMapper.scheduleToDTO(
                scheduleManagementUseCase.createSchedule(new CreateScheduleCommand(
                        staffId,
                        serviceId,
                        details.start(),
                        details.end(),
                        details.patterns()
                ))
        );
    }

    @PutMapping("/change/{scheduleId}/patterns")
    @PreAuthorize("hasPermission(#scheduleId, 'Schedule', 'Update')")
    ScheduleDTO changePatterns(@PathVariable("scheduleId") long scheduleId, @RequestBody List<SchedulePattern> patterns) {
        return dtoScheduleMapper.scheduleToDTO(
                scheduleManagementUseCase.changeSchedulePatterns(new ChangeSchedulePatternsCommand(
                        scheduleId,
                        patterns
                ))
        );
    }

    @PutMapping("/change/{scheduleId}/duration")
    @PreAuthorize("hasPermission(#scheduleId, 'Schedule', 'Update')")
    ScheduleDTO changeDuration(@PathVariable("scheduleId") long scheduleId, @RequestBody ScheduleDurationDTO duration) {
        return dtoScheduleMapper.scheduleToDTO(
                scheduleManagementUseCase.changeScheduleDuration(new ChangeScheduleDurationCommand(
                        scheduleId,
                        duration.start(),
                        duration.end()
                ))
        );
    }

    @PostMapping("/exception-days/create/{serviceId}/{staffId}")
    @PreAuthorize("hasPermission(#serviceId, 'ScheduleException', 'Create')")
    ScheduleExceptionDayDTO createExceptionDay(@PathVariable("serviceId") long serviceId, @PathVariable("staffId") long staffId, @RequestBody ScheduleExceptionDayCreationDTO exceptionDay) {
        return dtoScheduleExceptionDayMapper.scheduleExceptionDayToDTO(
                scheduleManagementUseCase.addOrUpdateScheduleExceptionDay(new CreateScheduleExceptionDayCommand(
                        staffId,
                        serviceId,
                        exceptionDay.date(),
                        exceptionDay.reservationSlots()
                ))
        );
    }

    @DeleteMapping("/remove/{scheduleId}")
    @PreAuthorize("hasPermission(#scheduleId, 'Schedule', 'Delete')")
    void removeSchedule(@PathVariable("scheduleId") long scheduleId) {
        scheduleManagementUseCase.removeSchedule(new RemoveScheduleCommand(
                scheduleId
        ));
    }
}
