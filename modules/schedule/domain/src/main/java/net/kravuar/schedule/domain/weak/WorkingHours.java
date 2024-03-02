package net.kravuar.schedule.domain.weak;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.domain.util.period.Period;
import net.kravuar.schedule.domain.util.period.StartBeforeEnd;

import java.time.LocalTime;

@Getter
@StartBeforeEnd
@RequiredArgsConstructor
public class WorkingHours implements Period<LocalTime> {
    @NotNull
    private final LocalTime start;
    @NotNull
    private final LocalTime end;
    @PositiveOrZero
    private final double cost;
    @Positive
    private final int maxReservations;
}
