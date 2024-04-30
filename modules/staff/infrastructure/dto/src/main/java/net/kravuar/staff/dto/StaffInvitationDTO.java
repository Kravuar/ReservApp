package net.kravuar.staff.dto;

import net.kravuar.staff.model.StaffInvitation;

import java.time.LocalDateTime;

public record StaffInvitationDTO(
        Long id,
        String sub,
        BusinessDTO business,
        LocalDateTime createdAt,
        StaffInvitation.Status status
) {
}
