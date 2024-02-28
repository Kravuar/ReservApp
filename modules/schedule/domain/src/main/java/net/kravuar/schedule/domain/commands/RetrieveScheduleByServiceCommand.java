package net.kravuar.schedule.domain.commands;

import java.time.LocalDate;

public record RetrieveScheduleByServiceCommand(
        long serviceId,
        /*
          Inclusive lower bound
         */
        LocalDate from,
        /*
          Inclusive upper bound
         */
        LocalDate to
) {
}
