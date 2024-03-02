package net.kravuar.schedule.domain.commands;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import net.kravuar.schedule.domain.SchedulePattern;

import java.util.List;

public record ChangeSchedulePatternsCommand(
        long scheduleId,
        @NotNull
        @Size(min = 1)
        List<@NotNull @Valid SchedulePattern> patterns
) {
}