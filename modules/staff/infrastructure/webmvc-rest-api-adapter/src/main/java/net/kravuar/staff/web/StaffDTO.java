package net.kravuar.staff.web;

record StaffDTO(
        Long id,
        String sub,
        BusinessDTO business,
        boolean active
) {}
