package net.kravuar.staff.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.staff.domain.DailySchedule;
import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.domain.commands.ServiceScheduleRetrievalCommand;
import net.kravuar.staff.domain.commands.StaffScheduleRetrievalCommand;
import net.kravuar.staff.ports.in.ScheduleRetrievalUseCase;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;
import java.util.SortedSet;

@RestController
@RequestMapping("/retrieval")
@RequiredArgsConstructor
class ScheduleRetrievalController {
    private final ScheduleRetrievalUseCase scheduleRetrieval;

    @GetMapping("/schedule-by-staff/{staffId}/{serviceId}/{startingFrom}")
    public Map<DayOfWeek, SortedSet<DailySchedule>> scheduleByStaff(@PathVariable("staffId") long staffId, @PathVariable("serviceId") long serviceId, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startingFrom) {
        return scheduleRetrieval.findScheduleWithChangesByStaff(new StaffScheduleRetrievalCommand(
                staffId,
                serviceId,
                startingFrom
        ));
    }

    @GetMapping("/schedule-by-service/{serviceId}/{startingFrom}")
    public Map<Staff, Map<DayOfWeek, SortedSet<DailySchedule>>> scheduleByService(@PathVariable("serviceId") long serviceId, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startingFrom) {
        return scheduleRetrieval.findScheduleWithChangesByService(new ServiceScheduleRetrievalCommand(
                serviceId,
                startingFrom
        ));
    }
}
