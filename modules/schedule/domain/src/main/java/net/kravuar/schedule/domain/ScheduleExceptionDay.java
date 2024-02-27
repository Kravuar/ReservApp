package net.kravuar.schedule.domain;

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
    private Long id;
    private final LocalDate date;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private final List<WorkingHours> workingHours;
}
