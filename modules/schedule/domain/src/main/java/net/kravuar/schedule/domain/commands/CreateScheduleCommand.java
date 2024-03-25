package net.kravuar.schedule.domain.commands;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.domain.SchedulePattern;
import net.kravuar.schedule.domain.util.period.Period;
import net.kravuar.schedule.domain.util.period.StartBeforeEnd;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
@StartBeforeEnd
public class CreateScheduleCommand implements Period<LocalDate> {
    private final long staffId;
    private final long serviceId;
    @NotNull
    @FutureOrPresent
    private final LocalDate start;
    @NotNull
    private final LocalDate end;
    @NotNull
    @Size(min = 1)
    private final List<@NotNull @Valid SchedulePattern> patterns;
}