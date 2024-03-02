package net.kravuar.schedule.web;

record ServiceDTO(
        long id,
        BusinessDTO businessDTO,
        boolean active
) {
}
