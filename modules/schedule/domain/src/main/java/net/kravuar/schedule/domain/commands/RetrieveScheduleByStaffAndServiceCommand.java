package net.kravuar.schedule.domain.commands;

import java.time.LocalDate;

public record RetrieveScheduleByStaffAndServiceCommand(
        long staffId,
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
