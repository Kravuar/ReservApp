package net.kravuar.schedule.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ReservationDTO(
        Long id,
        LocalDate date,
        LocalTime start,
        LocalTime end,
        String clientSub,
        StaffDTO staff,
        ServiceDTO service,
        boolean active,
        LocalDateTime createdAt
) {
}
