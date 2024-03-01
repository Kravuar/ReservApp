package net.kravuar.schedule.domain.halfbreeddomain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.domain.halfbreeddomain.WorkingHours;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class ScheduleExceptionDay {
    private final LocalDate date;
    private final List<WorkingHours> workingHours;
}
