package net.kravuar.schedule.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.kravuar.schedule.domain.util.period.PeriodsNotIntersect;
import net.kravuar.schedule.domain.weak.WorkingHours;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SchedulePattern {
    private Long id;
    @Positive
    private int repeatDays;
    @PositiveOrZero
    private int pauseDays;
    @NotNull
    @PeriodsNotIntersect
    private List<@NotNull @Valid WorkingHours> workingHours;
}
