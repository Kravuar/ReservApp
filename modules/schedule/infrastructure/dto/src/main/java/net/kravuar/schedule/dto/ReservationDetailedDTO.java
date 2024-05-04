package net.kravuar.schedule.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ReservationDetailedDTO (
        long id,
        LocalDate date,
        LocalTime start,
        LocalTime end,
        double cost,
        String clientSub,
        StaffDTO staff,
        ServiceDTO service,
        boolean active,
        LocalDateTime createdAt
) {
}
