package net.kravuar.schedule.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.domain.weak.WorkingHours;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class ScheduleExceptionDay {
    private Long id;
    private final LocalDate date;
    private Staff staff;
    private Service service;
    private final List<WorkingHours> workingHours;
}
