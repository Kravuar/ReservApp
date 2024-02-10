package net.kravuar.business.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class Business {
    private final Long id;
    private String name;
    private String email;
}
