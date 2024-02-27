package net.kravuar.accounts.domain.commands;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AccountCreationCommand(
        @Size(min = 3, max = 30)
        @NotNull
        String username,
        @NotBlank
        @NotNull
        String firstName,
        @NotBlank
        @NotNull
        String secondName,
        @NotNull
        @Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
        String email,
        @Size(min = 8, max = 30)
        @NotNull
        String password
) {
}
