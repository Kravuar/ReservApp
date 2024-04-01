package net.kravuar.schedule.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.ports.in.ReservationRetrievalUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservation/retrieval")
@RequiredArgsConstructor
class ReservationRetrievalController {
    private final ReservationRetrievalUseCase reservationRetrievalUseCase;
    private final DTOReservationMapper dtoReservationMapper;

    @GetMapping("/by-id/{reservationId}")
    @PreAuthorize("isAuthenticated() && hasPermission(#reservationId, 'Reservation', 'Read')")
    ReservationDTO byId(@PathVariable("reservationId") long reservationId) {
        return dtoReservationMapper.reservationToDTO(
                reservationRetrievalUseCase.findById(reservationId)
        );
    }

    @GetMapping("/by-service-and-staff/{serviceId}/{staffId}/{from}/{to}")
    Map<LocalDate, List<AnonymousReservationDTO>> byServiceAndStaff(@PathVariable("serviceId") long serviceId, @PathVariable("staffId") long staffId, @PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to) {
        return reservationRetrievalUseCase.findAllByStaff(staffId, from, to).entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .filter(reservation -> reservation.getService().getId().equals(serviceId)) // TODO: maybe add another usecase for that
                                .map(dtoReservationMapper::reservationToAnonymousDTO)
                                .toList()
                ));
    }

    @GetMapping("/by-staff/{staffId}/{from}/{to}")
    @PreAuthorize("isAuthenticated() && hasPermission(#staffId, 'ReservationsOfStaff', 'Read')")
    Map<LocalDate, List<ReservationDTO>> byStaff(@PathVariable("staffId") long staffId, @PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to) {
        return reservationRetrievalUseCase.findAllByStaff(staffId, from, to).entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(dtoReservationMapper::reservationToDTO)
                                .toList()
                ));
    }

    @GetMapping("/by-service/{serviceId}/{from}/{to}")
    Map<LocalDate, List<AnonymousReservationDTO>> byService(@PathVariable("serviceId") long serviceId, @PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to) {
        return reservationRetrievalUseCase.findAllByService(serviceId, from, to).entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(dtoReservationMapper::reservationToAnonymousDTO)
                                .toList()
                ));
    }

    @GetMapping("/my/{from}/{to}")
    @PreAuthorize("isAuthenticated()")
    Map<LocalDate, List<ReservationDTO>> my(@AuthenticationPrincipal Jwt jwt, @PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to) {
        return reservationRetrievalUseCase.findAllByClient(jwt.getSubject(), from, to).entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(dtoReservationMapper::reservationToDTO)
                                .toList()
                ));
    }
}
