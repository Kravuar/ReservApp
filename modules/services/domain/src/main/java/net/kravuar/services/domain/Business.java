package net.kravuar.services.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Business {
    private final long id;
    @NotNull
    @NotBlank
    private final String ownerSub;
    private boolean active;
}
