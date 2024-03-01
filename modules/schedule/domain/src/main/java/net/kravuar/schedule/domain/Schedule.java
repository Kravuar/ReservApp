package net.kravuar.schedule.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.kravuar.schedule.domain.halfbreeddomain.ScheduleExceptionDay;
import net.kravuar.schedule.domain.halfbreeddomain.SchedulePattern;
import net.kravuar.schedule.domain.util.period.Period;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Schedule implements Period<LocalDate> {
    private Long id;
    private LocalDate start;
    private LocalDate end;
    private final Staff staff;
    private final Service service;
    private List<SchedulePattern> patterns;
    private List<ScheduleExceptionDay> exceptionDays;

    private final LocalDateTime createdAt = LocalDateTime.now();
    private boolean active;
}
