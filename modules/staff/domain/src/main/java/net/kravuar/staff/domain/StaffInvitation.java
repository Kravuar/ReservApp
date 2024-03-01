package net.kravuar.staff.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class StaffInvitation {
    private Long id;
    private final String sub;
    private final Business business;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private Status status;

    public StaffInvitation(Long id, String sub, Business business) {
        this.id = id;
        this.sub = sub;
        this.business = business;
        this.status = Status.WAITING;
    }

    public enum Status {
        ACCEPTED,
        DECLINED,
        WAITING
    }
}
