package net.kravuar.schedule.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.domain.commands.RetrieveScheduleByServiceCommand;
import net.kravuar.schedule.domain.commands.RetrieveScheduleByStaffAndServiceCommand;
import net.kravuar.schedule.domain.commands.RetrieveScheduleExceptionDaysByStaffAndServiceCommand;
import net.kravuar.schedule.domain.weak.ReservationSlot;
import net.kravuar.schedule.ports.in.ScheduleRetrievalUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/retrieval")
@RequiredArgsConstructor
class ScheduleRetrievalController {
    private final ScheduleRetrievalUseCase scheduleRetrievalUseCase;
    private final DTOScheduleMapper dtoScheduleMapper;
    private final DTOStaffMapper dtoStaffMapper;
    private final DTOScheduleExceptionDayMapper dtoScheduleExceptionDayMapper;

    @GetMapping("/by-id/{scheduleId}/{activeOnly}")
    @PreAuthorize("hasPermission(#scheduleId, 'Schedule', 'ReadDirect')")
    ScheduleDTO byId(@PathVariable("scheduleId") long scheduleId, @PathVariable("activeOnly") boolean activeOnly) {
        return dtoScheduleMapper.scheduleToDTO(
                scheduleRetrievalUseCase.findScheduleById(scheduleId, activeOnly)
        );
    }

    @GetMapping("/by-staff-and-service/{staffId}/{serviceId}")
    @PreAuthorize("hasPermission(#serviceId, 'Schedule', 'Read')")
    List<ScheduleDTO> byStaffAndService(@PathVariable("staffId") long staffId, @PathVariable("serviceId") long serviceId) {
        return scheduleRetrievalUseCase.findActiveSchedulesByStaffAndService(staffId, serviceId).stream()
                .map(dtoScheduleMapper::scheduleToDTO)
                .toList();
    }

    @GetMapping("/exception-days/by-service-and-staff/{staffId}/{serviceId}/{from}/{to}")
    @PreAuthorize("hasPermission(#serviceId, 'ScheduleException', 'Read')")
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
    List<ScheduleByServiceDTO> byService(@PathVariable("serviceId") long serviceId, @PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to) {
        return scheduleRetrievalUseCase.findActiveScheduleByServiceInPerDay(new RetrieveScheduleByServiceCommand(
                serviceId,
                from,
                to
        )).entrySet().stream()
                .map(entry -> new ScheduleByServiceDTO(
                        dtoStaffMapper.staffToDTO(entry.getKey()),
                        entry.getValue()
                ))
                .toList();
    }

    @GetMapping("/by-service-and-staff/{serviceId}/{staffId}/{from}/{to}")
    Map<LocalDate, SortedSet<ReservationSlot>> byServiceAndStaff(@PathVariable("serviceId") long serviceId, @PathVariable("staffId") long staffId, @PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to) {
        return scheduleRetrievalUseCase.findActiveScheduleByStaffAndServiceInPerDay(new RetrieveScheduleByStaffAndServiceCommand(
                staffId,
                serviceId,
                from,
                to
        ));
    }
}
