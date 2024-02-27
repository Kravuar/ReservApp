package net.kravuar.schedule.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Service {
    private Long id;
    private Business business;
    private boolean active;
}
