package net.kravuar.staff.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kravuar.staff.domain.util.period.Period;
import net.kravuar.staff.domain.util.period.StartBeforeEnd;

import java.time.LocalTime;

@Builder
@Getter
@RequiredArgsConstructor
@StartBeforeEnd
public class WorkingHoursFragment implements Period, Comparable<Period> {
    @NotNull
    private final LocalTime start;
    @NotNull
    private final LocalTime end;
    @PositiveOrZero
    private final double cost;

    @Override
    public int compareTo(Period other) {
        return this.start.compareTo(other.getStart());
    }
}
