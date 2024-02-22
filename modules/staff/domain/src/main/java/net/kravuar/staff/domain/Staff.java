package net.kravuar.staff.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Staff {
    private final Long id;
    private final String sub;
    private final Business business;
    private String description;
    private boolean active;
}
