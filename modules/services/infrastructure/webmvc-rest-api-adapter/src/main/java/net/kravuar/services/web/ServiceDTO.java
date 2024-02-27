package net.kravuar.services.web;

record ServiceDTO(
        long id,
        BusinessDTO business,
        String name,
        boolean active,
        String description
) {}
