package net.kravuar.staff.domain.commands;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import net.kravuar.staff.domain.StaffSchedule;
import net.kravuar.staff.domain.util.period.StartBeforeEndDatetime;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class ChangeScheduleCommand {
    private final long id;
    private final long serviceId;
    private final long staffId;

    @NotNull
    @FutureOrPresent
    private final LocalDateTime validFrom = LocalDateTime.now();
    private final LocalDateTime validUntil; // Null means unlimited

    @NotNull
    private final DayOfWeek dayOfWeek;
    @PositiveOrZero
    private final double cost;

    private final Set<StaffSchedule.@StartBeforeEndDatetime WorkingHourFragment> fragments = new HashSet<>();
}