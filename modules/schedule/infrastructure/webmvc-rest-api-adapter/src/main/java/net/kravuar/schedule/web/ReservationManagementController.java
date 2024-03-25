package net.kravuar.schedule.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.domain.commands.CreateReservationCommand;
import net.kravuar.schedule.ports.in.ReservationManagementUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/reservation/management")
@RequiredArgsConstructor
class ReservationManagementController {
    private final ReservationManagementUseCase reservationManagementUseCase;
    private final DTOReservationMapper dtoReservationMapper;

    @PostMapping("/create/{staffId}/{serviceId}/{dateTime}")
    @PreAuthorize("isAuthenticated()")
    ReservationDTO createSchedule(@AuthenticationPrincipal Jwt jwt, @PathVariable("staffId") long staffId, @PathVariable("serviceId") long serviceId, @PathVariable("dateTime") LocalDateTime dateTime) {
        return dtoReservationMapper.reservationToDTO(
                reservationManagementUseCase.createReservation(new CreateReservationCommand(
                                jwt.getSubject(),
                                staffId,
                                serviceId,
                                dateTime
                        )
                )
        );
    }

    @DeleteMapping("/cancel/{reservationId}")
    @PreAuthorize("isAuthenticated() && @authorizationHandler.isClientOrStaff(#reservationId, authentication.details.subject)")
    void changePatterns(@PathVariable("reservationId") long reservationId) {
        reservationManagementUseCase.cancelReservation(reservationId);
    }
}
