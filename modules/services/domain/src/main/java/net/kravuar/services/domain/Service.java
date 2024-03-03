package net.kravuar.services.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Service {
    private Long id;
    private Business business;
    private String name;
    private boolean active;

    private String description;
}
