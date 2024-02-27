package net.kravuar.schedule.domain.util.period;

import java.time.temporal.Temporal;

// TODO: Cant do anything with the generics to remove code duplication
@StartBeforeEnd
public interface Period<T extends Temporal> {
    T getStart();
    T getEnd();
}
