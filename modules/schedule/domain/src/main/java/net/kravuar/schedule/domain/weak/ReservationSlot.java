package net.kravuar.schedule.domain.weak;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.kravuar.schedule.domain.util.period.Period;
import net.kravuar.schedule.domain.util.period.StartBeforeEnd;

import java.time.LocalTime;

@Getter
@StartBeforeEnd
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ReservationSlot implements Period<LocalTime>, Comparable<ReservationSlot> {
    @NotNull
    private LocalTime start;
    @NotNull
    private LocalTime end;
    @PositiveOrZero
    private double cost;
    @Positive
    private int maxReservations;

    @Override
    public int compareTo(ReservationSlot other) {
        return start.compareTo(other.getStart());
    }
}
