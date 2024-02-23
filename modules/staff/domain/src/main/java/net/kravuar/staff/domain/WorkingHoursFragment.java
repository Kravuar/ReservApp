package net.kravuar.staff.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kravuar.staff.domain.util.period.Period;

import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class WorkingHoursFragment implements Period, Comparable<Period> {
    private final LocalTime start;
    private final LocalTime end;
    private final double cost;

    @Override
    public int compareTo(Period other) {
        return this.start.compareTo(other.getStart());
    }
}
