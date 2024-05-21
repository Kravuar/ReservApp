package net.kravuar.schedule;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.schedule.domain.commands.RetrieveScheduleByServiceCommand;
import net.kravuar.schedule.domain.commands.RetrieveScheduleByServicesCommand;
import net.kravuar.schedule.domain.commands.RetrieveScheduleByStaffAndServiceCommand;
import net.kravuar.schedule.domain.commands.RetrieveScheduleExceptionDaysByStaffAndServiceCommand;
import net.kravuar.schedule.model.Schedule;
import net.kravuar.schedule.model.ScheduleExceptionDay;
import net.kravuar.schedule.model.Service;
import net.kravuar.schedule.model.Staff;
import net.kravuar.schedule.model.weak.ReservationSlot;
import net.kravuar.schedule.ports.in.ScheduleRetrievalUseCase;
import net.kravuar.schedule.ports.out.ScheduleRetrievalPort;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@AppComponent
@RequiredArgsConstructor
public class ScheduleRetrievalFacade implements ScheduleRetrievalUseCase {
    private final ScheduleRetrievalPort scheduleRetrievalPort;

    @Override
    public Schedule findScheduleById(long scheduleId, boolean activeOnly) {
        return scheduleRetrievalPort.findById(scheduleId, activeOnly);
    }

    @Override
    public List<Schedule> findActiveSchedulesByStaffAndService(long staffId, long serviceId) {
        return scheduleRetrievalPort.findActiveSchedulesByStaffAndService(
                staffId,
                serviceId,
                LocalDate.now()
        );
    }

    @Override
    public NavigableMap<LocalDate, SortedSet<ReservationSlot>> findActiveScheduleByStaffAndServiceInPerDay(RetrieveScheduleByStaffAndServiceCommand command) {
        List<Schedule> schedules = scheduleRetrievalPort.findActiveSchedulesByStaffAndService(command.getStaffId(), command.getServiceId(), command.getStart(), command.getEnd());
        Map<LocalDate, ScheduleExceptionDay> exceptionDays = scheduleRetrievalPort.findActiveExceptionDaysByStaffAndService(command.getStaffId(), command.getServiceId(), command.getStart(), command.getEnd());

        return Schedule.asPerDay(schedules, exceptionDays, command.getStart(), command.getEnd());
    }

    @Override
    public Map<Staff, NavigableMap<LocalDate, SortedSet<ReservationSlot>>> findActiveScheduleByServiceInPerDay(RetrieveScheduleByServiceCommand command) {
        Map<Staff, List<Schedule>> schedules = scheduleRetrievalPort.findActiveSchedulesByService(command.getServiceId(), command.getStart(), command.getEnd());
        Map<Staff, NavigableMap<LocalDate, ScheduleExceptionDay>> exceptionDays = scheduleRetrievalPort.findActiveExceptionDaysByService(command.getServiceId(), command.getStart(), command.getEnd());

        return perStaffToPerDay(schedules, exceptionDays, command.getStart(), command.getEnd());
    }

    @Override
    public Map<Service, Map<Staff, NavigableMap<LocalDate, SortedSet<ReservationSlot>>>> findActiveScheduleByServicesInPerDay(RetrieveScheduleByServicesCommand command) {
        Map<Service, Map<Staff, List<Schedule>>> schedules = scheduleRetrievalPort.findActiveSchedulesByServices(command.getServiceIds(), command.getStart(), command.getEnd());
        Map<Service, Map<Staff, NavigableMap<LocalDate, ScheduleExceptionDay>>> exceptionDays = scheduleRetrievalPort.findActiveExceptionDaysByServices(command.getServiceIds(), command.getStart(), command.getEnd());

        Set<Service> allServices = new HashSet<>(schedules.keySet());
        allServices.addAll(exceptionDays.keySet());

        return allServices.stream().collect(Collectors.toMap(
                Function.identity(),
                service -> {
                    Map<Staff, List<Schedule>> schedulesOfService = schedules.get(service);
                    Map<Staff, NavigableMap<LocalDate, ScheduleExceptionDay>> exceptionDaysOfService = exceptionDays.get(service);

                    return perStaffToPerDay(schedulesOfService, exceptionDaysOfService, command.getStart(), command.getEnd());
                }
        ));
    }


    @Override
    public NavigableMap<LocalDate, ScheduleExceptionDay> findActiveExceptionDaysByStaffAndService(RetrieveScheduleExceptionDaysByStaffAndServiceCommand command) {
        return scheduleRetrievalPort.findActiveExceptionDaysByStaffAndService(command.getStaffId(), command.getServiceId(), command.getStart(), command.getEnd());
    }

    private Map<Staff, NavigableMap<LocalDate, SortedSet<ReservationSlot>>> perStaffToPerDay(
            Map<Staff, List<Schedule>> schedulesOfService,
            Map<Staff, NavigableMap<LocalDate, ScheduleExceptionDay>> exceptionDaysOfService,
            LocalDate start,
            LocalDate end) {
        Set<Staff> allStaff = new HashSet<>(schedulesOfService.keySet());
        allStaff.addAll(exceptionDaysOfService.keySet());

        return allStaff.stream().collect(Collectors.toMap(
                Function.identity(),
                staff -> Schedule.asPerDay(
                        schedulesOfService.getOrDefault(staff, Collections.emptyList()),
                        exceptionDaysOfService.getOrDefault(staff, Collections.emptyNavigableMap()),
                        start,
                        end
                )
        ));
    }
}
