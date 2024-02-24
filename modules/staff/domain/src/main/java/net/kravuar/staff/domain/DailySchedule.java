package net.kravuar.staff.domain;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import net.kravuar.staff.domain.util.hours.WorkingHoursNotIntersecting;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.NavigableSet;
import java.util.TreeSet;

@Getter
@Builder
@WorkingHoursNotIntersecting
public class DailySchedule {
    @NotNull
    private final DayOfWeek dayOfWeek;
    @Builder.Default
    @NotNull
    @FutureOrPresent
    private final LocalDate validFrom = LocalDate.now();
    @Builder.Default
    @NotNull
    private final NavigableSet<@NotNull WorkingHoursFragment> workingHours = new TreeSet<>();
}
