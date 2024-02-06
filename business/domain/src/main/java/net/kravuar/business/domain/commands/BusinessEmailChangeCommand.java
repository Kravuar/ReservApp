package net.kravuar.business.domain.commands;

import jakarta.validation.constraints.Email;

public record BusinessEmailChangeCommand(long businessId, @Email String newEmail) {}
