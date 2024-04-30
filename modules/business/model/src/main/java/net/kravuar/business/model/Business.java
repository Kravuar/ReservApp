package net.kravuar.business.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Business {
    private Long id;
    private String ownerSub;
    private String name;
    private boolean active;

    private String description;
}
