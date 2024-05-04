package net.kravuar.pageable;

import java.util.List;

public record Page<T>(
        List<T> content,
        long totalElements,
        int totalPages
) {

}
