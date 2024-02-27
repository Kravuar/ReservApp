package net.kravuar.staff.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Staff {
    private Long id;
    private final String sub;
    private final Business business;
    private boolean active;

    private String description;
}
