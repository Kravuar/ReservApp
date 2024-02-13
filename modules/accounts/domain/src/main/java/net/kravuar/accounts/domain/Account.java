package net.kravuar.accounts.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Account {
    private final long id;
    private String username;
    private String email;
    private boolean emailVerified;
    private String passwordEncrypted;
}
