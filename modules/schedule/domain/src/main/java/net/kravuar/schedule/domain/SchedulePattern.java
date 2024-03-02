package net.kravuar.schedule.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.domain.util.period.PeriodsNotIntersect;
import net.kravuar.schedule.domain.weak.WorkingHours;

import java.util.List;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class SchedulePattern {
    private Long id;
    @Positive
    private final int repeatDays;
    @PositiveOrZero
    private final int pauseDays;
    @NotNull
    @PeriodsNotIntersect
    private final List<@NotNull @Valid WorkingHours> workingHours;
}
