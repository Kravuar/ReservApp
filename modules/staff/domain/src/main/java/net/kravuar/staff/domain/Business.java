package net.kravuar.staff.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Business {
    private final Long id;
    @NotNull
    @NotBlank
    private final String ownerSub;
    private boolean active;
}
