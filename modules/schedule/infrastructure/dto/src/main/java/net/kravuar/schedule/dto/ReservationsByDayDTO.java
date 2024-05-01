package net.kravuar.schedule.dto;

import java.time.LocalDate;
import java.util.List;

public record ReservationsByDayDTO (
        LocalDate date,
        List<ReservationDetailedDTO> reservations
) {}
