package net.kravuar.staff.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Staff {
    private Long id;
    private String sub;
    private Business business;
    private boolean active;

    private String description;
}
