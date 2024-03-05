package net.kravuar.schedule;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.schedule.domain.ReservationSlot;
import net.kravuar.schedule.domain.Schedule;
import net.kravuar.schedule.domain.ScheduleExceptionDay;
import net.kravuar.schedule.domain.Staff;
import net.kravuar.schedule.domain.commands.RetrieveScheduleByServiceCommand;
import net.kravuar.schedule.domain.commands.RetrieveScheduleByStaffAndServiceCommand;
import net.kravuar.schedule.domain.commands.RetrieveScheduleExceptionDaysByStaffAndServiceCommand;
import net.kravuar.schedule.ports.in.ScheduleRetrievalUseCase;
import net.kravuar.schedule.ports.out.ScheduleRetrievalPort;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedSet;
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
                LocalDate.now(),
                LocalDate.MAX
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

        return schedules.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> Schedule.asPerDay(
                        entry.getValue(),
                        exceptionDays.get(entry.getKey()),
                        command.getStart(),
                        command.getEnd()
                )
        ));
    }

    @Override
    public NavigableMap<LocalDate, ScheduleExceptionDay> findActiveExceptionDaysByStaffAndService(RetrieveScheduleExceptionDaysByStaffAndServiceCommand command) {
        return scheduleRetrievalPort.findActiveExceptionDaysByStaffAndService(command.getStaffId(), command.getServiceId(), command.getStart(), command.getEnd());
    }
}
