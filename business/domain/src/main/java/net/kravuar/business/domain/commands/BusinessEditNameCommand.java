package net.kravuar.business.domain.commands;

import jakarta.validation.constraints.Size;

public record BusinessEditNameCommand(long businessId, @Size(min = 3, max = 30) String newName) {}
