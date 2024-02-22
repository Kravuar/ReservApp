package net.kravuar.staff.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.kravuar.staff.domain.util.period.Period;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
public class StaffSchedule {
    private final Long id;
    private final Service service;
    private final Staff staff;
    private final LocalDateTime validFrom = LocalDateTime.now();
    private final DayOfWeek dayOfWeek;
    private final double cost;
    private final Set<WorkingHourFragment> workingHourFragments = new HashSet<>();

    @RequiredArgsConstructor
    @Getter
    public record WorkingHourFragment(
            LocalTime start,
            LocalTime end
    ) implements Period {}
}
