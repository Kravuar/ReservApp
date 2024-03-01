package net.kravuar.business.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Business {
    private Long id;
    private final String ownerSub;
    private String name;
    private boolean active;

    private String description;
}
