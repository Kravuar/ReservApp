package net.kravuar.services.web;

record ServiceDTO(
        long id,
        BusinessDTO business,
        boolean active,
        String name,
        String description
) {
}
