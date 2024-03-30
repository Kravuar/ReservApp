package net.kravuar.staff.web;

record StaffDTO(
        Long id,
        String sub,
        String name,
        String description,
//        URI picture,
        BusinessDTO business,
        boolean active
) {
}
