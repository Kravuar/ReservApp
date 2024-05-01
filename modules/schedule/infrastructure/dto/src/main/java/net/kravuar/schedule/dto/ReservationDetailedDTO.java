package net.kravuar.schedule.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class ReservationDetailedDTO extends ReservationDTO {
    private final String clientSub;

    public ReservationDetailedDTO(long id, LocalDate date, LocalTime start, LocalTime end, double cost, StaffDTO staff, ServiceDTO service, boolean active, LocalDateTime createdAt, String clientSub) {
        super(id, date, start, end, cost, staff, service, active, createdAt);
        this.clientSub = clientSub;
    }
}
