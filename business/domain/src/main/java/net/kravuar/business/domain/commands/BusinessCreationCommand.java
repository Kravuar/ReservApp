package net.kravuar.business.domain.commands;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record BusinessCreationCommand(
        @Size(min = 3, max = 30) String name,
        @Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$") String email
) {}
