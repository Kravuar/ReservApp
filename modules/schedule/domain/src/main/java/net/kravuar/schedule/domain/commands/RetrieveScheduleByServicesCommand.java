package net.kravuar.schedule.domain.commands;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.model.util.period.Period;
import net.kravuar.schedule.model.util.period.StartBeforeEnd;

import java.time.LocalDate;
import java.util.Set;

@StartBeforeEnd
@RequiredArgsConstructor
@Getter
public class RetrieveScheduleByServicesCommand implements Period<LocalDate> {
    private final Set<Long> serviceIds;
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
