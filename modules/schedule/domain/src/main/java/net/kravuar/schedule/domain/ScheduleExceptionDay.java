package net.kravuar.schedule.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.kravuar.schedule.domain.weak.WorkingHours;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleExceptionDay {
    private Long id;
    private LocalDate date;
    private Staff staff;
    private Service service;
    private List<WorkingHours> workingHours;
}
