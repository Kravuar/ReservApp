package net.kravuar.business.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull
    @Size(min = 3, max = 30)
    private String name;
    private boolean active;

    private String description;
}
