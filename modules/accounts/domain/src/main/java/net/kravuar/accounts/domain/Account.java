package net.kravuar.accounts.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Account {
    private final String sub;
    @Size(min = 3, max = 30)
    private String username;
    @NotBlank
    @NotNull
    private String firstName;
    @NotBlank
    @NotNull
    private String lastName;
    @NotNull
    @Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;
    @Size(min = 8, max = 30)
    @NotNull
    private String password;
}
