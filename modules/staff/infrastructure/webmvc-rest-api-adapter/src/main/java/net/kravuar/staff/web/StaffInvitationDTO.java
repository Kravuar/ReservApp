package net.kravuar.staff.web;

import net.kravuar.staff.model.StaffInvitation;

import java.time.LocalDateTime;

record StaffInvitationDTO(
        Long id,
        String sub,
        BusinessDTO business,
        LocalDateTime createdAt,
        StaffInvitation.Status status
) {
}
