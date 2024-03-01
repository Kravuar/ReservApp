package net.kravuar.schedule.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Staff {
    private Long id;
    private final Business business;
    private boolean active;
}
