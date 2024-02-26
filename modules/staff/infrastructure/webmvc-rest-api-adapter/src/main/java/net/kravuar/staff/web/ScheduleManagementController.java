package net.kravuar.staff.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.staff.domain.commands.ChangeDailyScheduleCommand;
import net.kravuar.staff.ports.in.ScheduleManagementUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
@RequiredArgsConstructor
class ScheduleManagementController {
    private final ScheduleManagementUseCase scheduleManagement;

    @PostMapping("/update-schedule")
    @PreAuthorize("isAuthenticated() && @authorizationHandler.isOwnerOfService(#command.serviceId, authentication.details.subject)")
    public void updateSchedule(@RequestBody ChangeDailyScheduleCommand command) {
        scheduleManagement.updateSchedule(command);
    }
}
