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
    private boolean active;

    private String name;
    private String description;
}