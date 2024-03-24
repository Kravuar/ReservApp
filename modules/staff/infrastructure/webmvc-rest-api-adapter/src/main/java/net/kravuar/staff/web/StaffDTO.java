package net.kravuar.staff.web;

record StaffDTO(
        Long id,
        String sub,
        String name,
//        URI picture,
        BusinessDTO business,
        boolean active
) {
}
