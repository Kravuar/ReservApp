package net.kravuar.staff.web;

import net.kravuar.staff.domain.StaffInvitation;

import java.time.LocalDateTime;

record StaffInvitationDTO(
        Long id,
        String sub,
        BusinessDTO business,
        LocalDateTime createdAt,
        StaffInvitation.Status status
) {
}
