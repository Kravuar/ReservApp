package net.kravuar.schedule.domain.commands;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kravuar.staff.model.util.period.Period;
import net.kravuar.staff.model.util.period.StartBeforeEnd;

import java.time.LocalDate;

@StartBeforeEnd
@RequiredArgsConstructor
@Getter
public class RetrieveScheduleByServiceCommand implements Period<LocalDate> {
    private final long serviceId;
    /*
      Inclusive lower bound
     */
    @NotNull
    @FutureOrPresent
    private final LocalDate start;
    /*
      Inclusive upper bound
     */
    @NotNull
    private final LocalDate end;
}
