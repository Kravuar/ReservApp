package net.kravuar.schedule.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    private Long id;
    private LocalDateTime dateTime;
    private String clientSub;
    private Staff staff;
    private Service service;
    private boolean active;

    private final LocalDateTime createdAt = LocalDateTime.now();
}
