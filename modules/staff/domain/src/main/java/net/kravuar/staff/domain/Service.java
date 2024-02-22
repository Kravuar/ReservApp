package net.kravuar.staff.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Service {
    private final Long id;
    private Business business;
    private boolean active;
}
