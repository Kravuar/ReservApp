package net.kravuar.schedule.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ReservationDTO(
        long id,
        LocalDate date,
        LocalTime start,
        LocalTime end,
        double cost,
        StaffDTO staff,
        ServiceDTO service,
        boolean active,
        LocalDateTime createdAt
) {}
