package net.kravuar.staff.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Business {
    private Long id;
    private final String ownerSub;
    private boolean active;
}
