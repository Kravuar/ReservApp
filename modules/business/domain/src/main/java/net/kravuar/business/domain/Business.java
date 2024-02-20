package net.kravuar.business.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Business {
    private final Long id;
    private final String ownerSub;
    private String name;
    private boolean active;
}
