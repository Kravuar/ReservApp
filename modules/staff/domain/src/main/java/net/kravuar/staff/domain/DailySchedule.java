package net.kravuar.staff.domain;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import net.kravuar.staff.domain.util.hours.WorkingHoursNotIntersecting;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NavigableSet;
import java.util.TreeSet;

@Getter
@Builder
@WorkingHoursNotIntersecting
public class DailySchedule {
    private Long id;
    @NotNull
    private final DayOfWeek dayOfWeek;
    @Builder.Default
    @NotNull
    @FutureOrPresent
    private final LocalDate validFrom = LocalDate.now();
    private final LocalDateTime disabledAt;
    @Builder.Default
    @NotNull
    private final NavigableSet<@NotNull WorkingHoursFragment> workingHours = new TreeSet<>();
}
