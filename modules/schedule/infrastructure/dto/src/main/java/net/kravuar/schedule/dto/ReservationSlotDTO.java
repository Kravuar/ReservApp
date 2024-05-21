package net.kravuar.schedule.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationSlotDTO(
        LocalDate date,
        LocalTime start,
        LocalTime end,
        double cost,
        Integer maxReservations,
        StaffDTO staff,
        ServiceDTO service
) {}
