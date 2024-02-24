package net.kravuar.staff.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Service {
    private final Long id;
    @NotNull
    private Business business;
    private boolean active;
}
