package net.kravuar.schedule.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.domain.util.period.PeriodsNotIntersect;
import net.kravuar.schedule.domain.weak.WorkingHours;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class ScheduleExceptionDay {
    private Long id;
    private final LocalDate date;
    @NotNull
    @PeriodsNotIntersect
    private final List<@NotNull @Valid WorkingHours> workingHours;
}
