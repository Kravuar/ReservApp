package net.kravuar.schedule.dto;

import java.time.LocalDate;
import java.util.List;

public record AnonymousReservationsByDayDTO(
        LocalDate date,
        List<ReservationDTO> reservations
) {}
