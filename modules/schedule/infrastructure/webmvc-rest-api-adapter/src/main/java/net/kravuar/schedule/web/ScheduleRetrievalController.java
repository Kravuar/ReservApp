package net.kravuar.schedule.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.domain.commands.RetrieveScheduleByServiceCommand;
import net.kravuar.schedule.domain.commands.RetrieveScheduleByServicesCommand;
import net.kravuar.schedule.domain.commands.RetrieveScheduleByStaffAndServiceCommand;
import net.kravuar.schedule.domain.commands.RetrieveScheduleExceptionDaysByStaffAndServiceCommand;
import net.kravuar.schedule.dto.*;
import net.kravuar.schedule.model.Service;
import net.kravuar.schedule.model.Staff;
import net.kravuar.schedule.model.weak.ReservationSlot;
import net.kravuar.schedule.ports.in.ScheduleRetrievalUseCase;
import net.kravuar.schedule.ports.in.ServiceRetrievalUseCase;
import net.kravuar.schedule.ports.in.StaffRetrievalUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/retrieval")
@RequiredArgsConstructor
class ScheduleRetrievalController {
    private final ScheduleRetrievalUseCase scheduleRetrievalUseCase;
    private final ServiceRetrievalUseCase serviceRetrievalUseCase;
    private final StaffRetrievalUseCase staffRetrievalUseCase;
    private final DTOScheduleMapper dtoScheduleMapper;
    private final DTOStaffMapper dtoStaffMapper;
    private final DTOServiceMapper dtoServiceMapper;
    private final DTOScheduleExceptionDayMapper dtoScheduleExceptionDayMapper;

    @GetMapping("/by-id/{scheduleId}")
    @PreAuthorize("hasPermission(#scheduleId, 'Schedule', 'Read')")
    ScheduleDTO byId(@PathVariable("scheduleId") long scheduleId) {
        return dtoScheduleMapper.scheduleToDTO(
                scheduleRetrievalUseCase.findScheduleById(scheduleId, false)
        );
    }

    @GetMapping("/active-by-service-and-staff/{serviceId}/{staffId}")
    @PreAuthorize("hasPermission(#serviceId, 'Schedule', 'Read')")
    List<ScheduleDTO> byServiceAndStaff(@PathVariable("staffId") long staffId, @PathVariable("serviceId") long serviceId) {
        return scheduleRetrievalUseCase.findActiveSchedulesByStaffAndService(staffId, serviceId).stream()
                .map(dtoScheduleMapper::scheduleToDTO)
                .toList();
    }

    @GetMapping("/exception-days/by-service-and-staff/{staffId}/{serviceId}/{from}/{to}")
    @PreAuthorize("hasPermission(#serviceId, 'ScheduleException', 'Read')")
    List<ScheduleExceptionDayDTO> scheduleExceptionDaysByServiceAndStaff(@PathVariable("serviceId") long serviceId, @PathVariable("staffId") long staffId, @PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to) {
        return scheduleRetrievalUseCase.findActiveExceptionDaysByStaffAndService(
                        new RetrieveScheduleExceptionDaysByStaffAndServiceCommand(
                                staffId,
                                serviceId,
                                from,
                                to
                        )
                ).values().stream()
                .map(dtoScheduleExceptionDayMapper::scheduleExceptionDayToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-services/{from}/{to}")
    List<ReservationSlotDTO> byServices(@RequestParam("serviceIds") Set<Long> serviceIds, @PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to) {
        return scheduleRetrievalUseCase.findActiveScheduleByServicesInPerDay(new RetrieveScheduleByServicesCommand(
                serviceIds,
                from,
                to
        )).entrySet().stream().flatMap(byServiceEntry -> {
            ServiceDTO serviceDTO = dtoServiceMapper.serviceToDTO(byServiceEntry.getKey());
            return byServiceEntry.getValue().entrySet().stream()
                    .flatMap(perStaffToFlat(serviceDTO));
        }).toList();
    }

    @GetMapping("/by-service/{serviceId}/{from}/{to}")
    List<ReservationSlotDTO> byService(@PathVariable("serviceId") long serviceId, @PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to) {
        Service service = serviceRetrievalUseCase.findActiveById(serviceId);
        ServiceDTO serviceDTO = dtoServiceMapper.serviceToDTO(service);

        return scheduleRetrievalUseCase.findActiveScheduleByServiceInPerDay(new RetrieveScheduleByServiceCommand(
                serviceId,
                from,
                to
        )).entrySet().stream()
                .flatMap(perStaffToFlat(serviceDTO))
                .toList();
    }

    @GetMapping("/by-service-and-staff/{serviceId}/{staffId}/{from}/{to}")
    List<ReservationSlotDTO> byServiceAndStaff(@PathVariable("serviceId") long serviceId, @PathVariable("staffId") long staffId, @PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to) {
        Service service = serviceRetrievalUseCase.findActiveById(serviceId);
        ServiceDTO serviceDTO = dtoServiceMapper.serviceToDTO(service);
        Staff staff = staffRetrievalUseCase.findById(staffId);
        StaffDTO staffDTO = dtoStaffMapper.staffToDTO(staff);

        return scheduleRetrievalUseCase.findActiveScheduleByStaffAndServiceInPerDay(new RetrieveScheduleByStaffAndServiceCommand(
                staffId,
                serviceId,
                from,
                to
        )).entrySet().stream().flatMap(byDateEntry -> byDateEntry.getValue().stream()
                .map(slot -> dtoScheduleMapper.slotToDTO(
                        slot,
                        byDateEntry.getKey(),
                        serviceDTO,
                        staffDTO
                ))).toList();
    }

    private Function<Map.Entry<Staff, NavigableMap<LocalDate, SortedSet<ReservationSlot>>>, Stream<? extends ReservationSlotDTO>> perStaffToFlat(ServiceDTO serviceDTO) {
        return byStaffEntry -> {
            StaffDTO staffDTO = dtoStaffMapper.staffToDTO(byStaffEntry.getKey());
            return byStaffEntry.getValue().entrySet().stream()
                    .flatMap(byDateEntry -> byDateEntry.getValue().stream()
                            .map(slot -> dtoScheduleMapper.slotToDTO(
                                    slot,
                                    byDateEntry.getKey(),
                                    serviceDTO,
                                    staffDTO
                            )));
        };
    }
}
