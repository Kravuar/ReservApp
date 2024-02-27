package net.kravuar.staff.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class StaffInvitation {
    private Long id;
    private final String sub;
    private final Business business;
    private final LocalDateTime createdAt = LocalDateTime.now();
    @Builder.Default
    private Status status = Status.WAITING;

    public enum Status {
        ACCEPTED,
        DECLINED,
        WAITING
    }
}
