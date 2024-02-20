package net.kravuar.services.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Service {
    private final Long id;
    private final Business business;
    private String name;
    private boolean active;
}
