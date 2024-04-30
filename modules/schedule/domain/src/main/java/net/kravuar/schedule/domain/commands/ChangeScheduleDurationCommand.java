package net.kravuar.schedule.domain.commands;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kravuar.staff.model.util.period.Period;
import net.kravuar.staff.model.util.period.StartBeforeEnd;

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
@StartBeforeEnd
public class ChangeScheduleDurationCommand implements Period<LocalDate> {
    private final long scheduleId;
    @NotNull
    @FutureOrPresent
    private final LocalDate start;
    @NotNull
    private final LocalDate end;
}