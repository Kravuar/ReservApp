package net.kravuar.services.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Service {
    private final Long id;
    @NotNull
    private final Business business;
    @NotNull
    @Size(min = 3, max = 30)
    private String name;
    private boolean active;
}
