package net.kravuar.schedule.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.domain.Staff;
import net.kravuar.schedule.domain.commands.RetrieveScheduleByServiceCommand;
import net.kravuar.schedule.domain.commands.RetrieveScheduleByStaffAndServiceCommand;
import net.kravuar.schedule.domain.commands.RetrieveScheduleExceptionDaysByStaffAndServiceCommand;
import net.kravuar.schedule.domain.weak.WorkingHours;
import net.kravuar.schedule.ports.in.ScheduleRetrievalUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/retrieval")
@RequiredArgsConstructor
class ScheduleRetrievalController {
    private final ScheduleRetrievalUseCase scheduleRetrievalUseCase;
    private final DTOScheduleMapper dtoScheduleMapper;
    private final DTOScheduleExceptionDayMapper dtoScheduleExceptionDayMapper;

    @GetMapping("/by-id/{scheduleId}/{activeOnly}")
    @PreAuthorize("isAuthenticated() && @authorizationHandler.isOwnerOfScheduleBusiness(#scheduleId, authentication.details.subject)")
    ScheduleDTO byId(@PathVariable("scheduleId") long scheduleId, @PathVariable("activeOnly") boolean activeOnly) {
        return dtoScheduleMapper.scheduleToDTO(
                scheduleRetrievalUseCase.findScheduleById(scheduleId, activeOnly)
        );
    }

    @GetMapping("/exception-days/by-service-and-staff/{staffId}/{serviceId}/{from}/{to}")
    @PreAuthorize("isAuthenticated() && @authorizationHandler.isOwnerOfServiceBusiness(#serviceId, authentication.details.subject)")
    Map<LocalDate, ScheduleExceptionDayDTO> scheduleExceptionDaysByServiceAndStaff(@PathVariable("serviceId") long serviceId, @PathVariable("staffId") long staffId, @PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to) {
        return scheduleRetrievalUseCase.findActiveExceptionDaysByStaffAndService(
                        new RetrieveScheduleExceptionDaysByStaffAndServiceCommand(
                                staffId,
                                serviceId,
                                from,
                                to
                        )
                ).entrySet()
                .stream()
                .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> dtoScheduleExceptionDayMapper.scheduleExceptionDayToDTO(entry.getValue())
                        )
                );
    }

    @GetMapping("/by-service/{serviceId}/{from}/{to}")
    Map<Staff, Map<LocalDate, List<WorkingHours>>> byService(@PathVariable("serviceId") long serviceId, @PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to) {
        return scheduleRetrievalUseCase.findActiveScheduleByServiceInPerDay(new RetrieveScheduleByServiceCommand(
                serviceId,
                from,
                to
        ));
    }

    @GetMapping("/by-service-and-staff/{serviceId}/{staffId}/{from}/{to}")
    Map<LocalDate, List<WorkingHours>> byServiceAndStaff(@PathVariable("serviceId") long serviceId, @PathVariable("staffId") long staffId, @PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to) {
        return scheduleRetrievalUseCase.findActiveScheduleByStaffAndServiceInPerDay(new RetrieveScheduleByStaffAndServiceCommand(
                serviceId,
                staffId,
                from,
                to
        ));
    }
}