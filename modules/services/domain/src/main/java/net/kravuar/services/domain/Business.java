package net.kravuar.services.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Business {
    private Long id;
    private final String ownerSub;
    private boolean active;
}
