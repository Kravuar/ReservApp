package net.kravuar.staff.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class StaffInvitation {
    private final Long id;
    @NotNull
    @NotBlank
    private final String sub;
    @NotNull
    private final Business business;
    @NotNull
    private final LocalDateTime createdAt;
    @NotNull
    @Builder.Default
    private Status status = Status.WAITING;

    public enum Status {
        ACCEPTED,
        DECLINED,
        WAITING
    }
}
