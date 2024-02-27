package net.kravuar.schedule.domain.halfbreeddomain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.domain.util.period.PeriodsNotIntersect;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SchedulePattern {
    @Positive
    private final int repeatDays;
    @PositiveOrZero
    private final int pauseDays;
    @NotNull
    @PeriodsNotIntersect
    private final List<@NotNull @Valid WorkingHours> workingHours;
}
