package net.kravuar.business.domain.commands;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BusinessChangeNameCommand(
        long businessId,
        @NotNull
        @Size(min = 3, max = 30)
        String newName
) {
}
