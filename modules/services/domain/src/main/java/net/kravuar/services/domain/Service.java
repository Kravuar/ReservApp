package net.kravuar.services.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Service {
    private Long id;
    private final Business business;
    private String name;
    private boolean active;

    private String description;
}
