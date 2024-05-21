package net.kravuar.accounts.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Account {
    private final String sub;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
