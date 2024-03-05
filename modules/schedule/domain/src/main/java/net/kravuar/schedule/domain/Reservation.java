package net.kravuar.schedule.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    private Long id;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private String clientSub;
    private Staff staff;
    private Service service;
    private boolean active;

    private final LocalDateTime createdAt = LocalDateTime.now();
}
