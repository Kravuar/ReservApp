package net.kravuar.schedule.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

record AnonymousReservationDTO(
        Long id,
        LocalDate date,
        LocalTime start,
        LocalTime end,
        StaffDTO staff,
        ServiceDTO service,
        boolean active,
        LocalDateTime createdAt
) {
}
