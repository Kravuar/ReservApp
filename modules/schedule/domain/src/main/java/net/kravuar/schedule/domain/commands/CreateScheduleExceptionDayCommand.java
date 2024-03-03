package net.kravuar.schedule.domain.commands;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import net.kravuar.schedule.domain.util.period.PeriodsNotIntersect;
import net.kravuar.schedule.domain.weak.WorkingHours;

import java.time.LocalDate;
import java.util.List;

public record CreateScheduleExceptionDayCommand(
        long staffId,
        long serviceId,
        @NotNull
        @FutureOrPresent
        LocalDate date,
        @NotNull
        @PeriodsNotIntersect
        List<@NotNull @Valid WorkingHours> workingHours
) {}