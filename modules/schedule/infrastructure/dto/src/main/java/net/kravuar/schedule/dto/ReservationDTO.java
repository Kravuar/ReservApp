package net.kravuar.schedule.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RequiredArgsConstructor
@Getter
@Setter
public class ReservationDTO {
    private final long id;
    private final LocalDate date;
    private final LocalTime start;
    private final LocalTime end;
    private final double cost;
    private final StaffDTO staff;
    private final ServiceDTO service;
    private final boolean active;
    private final LocalDateTime createdAt;
}
