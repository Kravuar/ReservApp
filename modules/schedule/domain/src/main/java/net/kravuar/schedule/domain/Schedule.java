package net.kravuar.schedule.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.kravuar.schedule.domain.util.period.Period;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Schedule implements Period<LocalDate> {
    private Long id;
    private LocalDate start;
    private LocalDate end;
    private Staff staff;
    private Service service;
    private List<SchedulePattern> patterns;

    private final LocalDateTime createdAt = LocalDateTime.now();
    private boolean active;
}
