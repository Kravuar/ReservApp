package net.kravuar.schedule.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Service {
    private Long id;
    private Business business;
    private boolean active;
}
