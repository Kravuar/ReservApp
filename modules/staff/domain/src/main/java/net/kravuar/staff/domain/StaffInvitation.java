package net.kravuar.staff.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StaffInvitation {
    private Long id;
    private String sub;
    private Business business;
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
