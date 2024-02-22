package net.kravuar.staff.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class StaffInvitation {
    private final String sub;
    private final Business business;
    private final LocalDateTime createdAt;
    private final Result result = Result.WAITING;

    public enum Result {
        ACCEPTED,
        DECLINED,
        WAITING
    }
}
